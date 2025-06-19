export interface TranslateRequest {
    q: string;
    source: string;
    target: string;
    format?: string;
    alternatives?: number;
    api_key?: string;
}

export interface TranslateResponse {
    translatedText: string;
}

// ...existing code...

export const translateFields = async (
    input: Partial<Record<string, unknown>>,
    fieldsToTranslate: string[],
    locale: string = "en"
): Promise<Partial<Record<string, string>>> => {
    try {
        const fieldsForTranslation: Record<string, string> = {};

        for (const field of fieldsToTranslate) {
            const value = input[field];
            if (typeof value === 'string' && value.trim() !== '') {
                fieldsForTranslation[field] = value;
            }
        }

        if (Object.keys(fieldsForTranslation).length === 0) {
            return {};
        }

        const response = await fetch("http://127.0.0.1:5000/translate", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                q: fieldsForTranslation,
                source: "vi",
                target: locale,
                format: "json",
                alternatives: 1,
                api_key: ""
            }),
        });

        if (!response.ok) {
            throw new Error(`Translation API error: ${response.statusText}`);
        }

        const translatedFields = await response.json(); // expected { gender: 'Male', ... }

        return translatedFields;
    } catch (error) {
        console.error("Translation failed:", error);
        return {};
    }
};