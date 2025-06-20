import { useQuery } from '@tanstack/react-query';
import { fetchTranscript } from '@/libs/services/transcriptService';

export const useTranscripts = () => {
    const { data, error, isLoading } = useQuery({
        queryKey: ['transcripts'],
        queryFn: fetchTranscript,
    });

    return { data, error, isLoading };
};
