package org.example.backend.service.Import;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class XMLImportService implements ImportService {

    @Override
    public <T> List<T> importData(byte[] data, Class<T> type) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        return xmlMapper.readValue(data, xmlMapper.getTypeFactory().constructCollectionType(List.class, type));
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
