const translationCache = new Map<string, string>();

const translateTextAsync = async (text: string): Promise<string> => {
    const currentLocale = Cookies.get('NEXT_LOCALE') || 'vi';

    // Nếu đang dùng tiếng Anh → không dịch
    if (currentLocale === 'en') return text;

    // Nếu đã cache
    if (translationCache.has(text)) return translationCache.get(text)!;

    // Tách prefix dạng "Xóa thất bại: Something in English"
    const match = /^([^:]+):\s*(.+)$/.exec(text);
    const prefix = match?.[1]?.trim();
    const mainContent = match?.[2]?.trim();
    const contentToTranslate = mainContent || text;
    const hasPrefix = !!(prefix && mainContent);

    // Nếu không có prefix thì không dịch, trả về text gốc
    if (!hasPrefix) {
        translationCache.set(text, text);
        return text;
    }

    try {
        const res = await fetch("http://127.0.0.1:5000/translate", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                q: contentToTranslate,
                source: "auto",
                target: currentLocale,
                format: "text",
                alternatives: 1,
                api_key: ""
            }),
        });

        const json = await res.json();
        let translated = json.translatedText || `${contentToTranslate}`;

        if (hasPrefix) {
            translated = `${prefix}: ${translated}`;
        }

        translationCache.set(text, translated);
        return translated;
    } catch (error) {
        console.warn("⚠️ API dịch lỗi:", error);
        translationCache.set(text, text); // fallback không dịch
        return text;
    }
};
