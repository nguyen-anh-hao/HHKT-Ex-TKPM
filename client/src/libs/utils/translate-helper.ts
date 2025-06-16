import Cookies from 'js-cookie';

// 1. Danh sách field cần dịch theo từng kiểu dữ liệu
const translatableFields: Record<string, string[]> = {
    StudentResponse: ['gender', 'faculty', 'program', 'studentStatus', 'nationality'],
    UpdateStudentRequest: ['gender', 'nationality'],
    CreateStudentRequest: ['gender', 'nationality'],
    
    ReferenceResponse: ['facultyName', 'programName', 'studentStatusName'],
    CreateReferenceRequest: ['facultyName', 'programName', 'studentStatusName'],
    UpdateReferenceRequest: ['facultyName', 'programName', 'studentStatusName'],

    StatusRuleResponse: ['currentStatusName', 'allowedTransitionName'],
    CourseResponse: ['courseName', 'description', 'facultyName'],
    CreateCourseRequest: ['courseCode', 'courseName', 'description'],
    UpdateCourseRequest: ['courseCode', 'courseName', 'description'],

    ClassResponse: ['courseName', 'schedule', 'facultyName'],
};

function getTranslatableFields(typeName: string): string[] {
    return translatableFields[typeName] ?? [];
}

// 2. Cache bản dịch sử dụng localStorage
const CACHE_KEY_ORIG_TO_TRANS = 'translationCacheOrigToTrans';
const CACHE_KEY_TRANS_TO_ORIG = 'translationCacheTransToOrig';

function getCacheObject(key: string): Record<string, string> {
    try {
        const cache = localStorage.getItem(key);
        return cache ? JSON.parse(cache) : {};
    } catch {
        return {};
    }
}

function setCacheObject(key: string, obj: Record<string, string>) {
    try {
        localStorage.setItem(key, JSON.stringify(obj));
    } catch {
        // ignore
    }
}

function setTranslationCache(original: string, translated: string) {
    // Store both directions for easier lookup
    const cacheOrigToTrans = getCacheObject(CACHE_KEY_ORIG_TO_TRANS);
    cacheOrigToTrans[original] = translated;
    setCacheObject(CACHE_KEY_ORIG_TO_TRANS, cacheOrigToTrans);
    
    const cacheTransToOrig = getCacheObject(CACHE_KEY_TRANS_TO_ORIG);
    cacheTransToOrig[translated] = original;
    setCacheObject(CACHE_KEY_TRANS_TO_ORIG, cacheTransToOrig);
}

function getOriginalFromTranslated(translated: string): string | undefined {
    const cache = getCacheObject(CACHE_KEY_TRANS_TO_ORIG);
    return cache[translated];
}

function getTranslatedFromOriginal(original: string): string | undefined {
    const cache = getCacheObject(CACHE_KEY_ORIG_TO_TRANS);
    return cache[original];
}

// 3. Gọi API Flask để dịch
async function translateText(text: string, source = 'vi', target = 'en'): Promise<string> {
    // Check cache first
    const cached = getTranslatedFromOriginal(text);
    if (cached) return cached;
    
    const locale = Cookies.get('NEXT_LOCALE') || 'vi';
    if (locale !== 'en') return text;

    const res = await fetch("http://127.0.0.1:5000/translate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            q: text,
            source: source,
            target: target,
            format: "text",
            alternatives: 1,
            api_key: ""
        }),
    });

    if (!res.ok) {
        console.error(`Failed to translate: ${text}`);
        return text;
    }

    const data = await res.json();
    return data?.translatedText || text;
}

// 4. Dịch dữ liệu trả về từ API
export async function translateResponse<T extends object>(
    data: T,
    typeName: string
): Promise<T> {
    const locale = Cookies.get('NEXT_LOCALE') || 'vi';
    if (locale !== 'en') return data; // không cần dịch nếu không phải tiếng Anh

    const fieldsToTranslate = getTranslatableFields(typeName);
    const result = { ...data };

    for (const key of fieldsToTranslate) {
        const value = (data as any)[key];
        if (typeof value === 'string') {
            const translated = await translateText(value, 'vi', 'en');
            setTranslationCache(value, translated); // lưu original => translated
            (result as any)[key] = translated;
        }
    }

    return result;
}

// 5. Dịch ngược request trước khi gửi lên
export async function translateRequest<T extends object>(data: T, typeName: string): Promise<T> {
    const locale = Cookies.get('NEXT_LOCALE') || 'vi';
    if (locale !== 'en') return data;

    const fieldsToTranslate = getTranslatableFields(typeName);
    const result = { ...data };

    for (const key of fieldsToTranslate) {
        const value = (data as any)[key];
        if (typeof value === 'string') {
            let original = getOriginalFromTranslated(value);
            if (!original) {
                // Dùng API để dịch ngược lại sang tiếng Việt
                original = await translateText(value, 'en', 'vi');
                setTranslationCache(original, value); // lưu vào cache
            }
            (result as any)[key] = original;
        }
    }

    return result;
}

// 6. Dịch mảng dữ liệu trả về từ API
export async function translateArrayResponse<T extends object>(
    data: T[],
    typeName: string
): Promise<T[]> {
    const locale = Cookies.get('NEXT_LOCALE') || 'vi';
    if (locale !== 'en') return data;

    return Promise.all(data.map((item) => translateResponse(item, typeName)));
}

export async function translateArrayRequest<T extends object>(
    data: T[],
    typeName: string
): Promise<T[]> {
    const locale = Cookies.get('NEXT_LOCALE') || 'vi';
    if (locale !== 'en') return data;

    return Promise.all(data.map((item) => translateRequest(item, typeName)));
}