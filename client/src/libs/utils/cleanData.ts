export const cleanData = (data: any) => {
    return Object.fromEntries(Object.entries(data).filter(([_, value]) => 
        value !== null && 
        value !== undefined && 
        value !== '' && 
        (Array.isArray(value) || typeof value === 'string' ? value.length !== 0 : true)
    ));
}