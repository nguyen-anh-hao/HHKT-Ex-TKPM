package org.example.backend.service.Import;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.backend.dto.response.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class XMLImportServiceTest {

    private XMLImportService xmlImportService;
    private XmlMapper xmlMapper;
    private byte[] testXmlData;
    private List<StudentResponse> expectedStudentList;

    @BeforeEach
    void setUp() {
        xmlImportService = new XMLImportService();
        xmlMapper = mock(XmlMapper.class);
    }

    @Test
    void getFileExtension_ShouldReturnXml() {
        // When
        String extension = xmlImportService.getFileExtension();

        // Then
        assertEquals("xml", extension);
    }

    @Test
    void getMediaType_ShouldReturnApplicationXml() {
        // When
        MediaType mediaType = xmlImportService.getMediaType();

        // Then
        assertEquals(MediaType.APPLICATION_XML, mediaType);
    }
}