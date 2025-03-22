package org.example.backend.service.export;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class XMLExportService implements ExportService {
    @Override
    public byte[] exportData(List<?> data) throws IOException {
        if (data == null || data.isEmpty()) {
            return "<root></root>".getBytes();
        }

        XmlMapper xmlMapper = new XmlMapper();

        xmlMapper.registerModule(new JavaTimeModule());
        return xmlMapper.writeValueAsBytes(data);
    }

    @Override
    public String getFileExtension() {
        return "xml";
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.APPLICATION_XML;
    }
}
