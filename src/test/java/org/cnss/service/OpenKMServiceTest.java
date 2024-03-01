package org.cnss.service;


import org.cnss.Config;
import org.cnss.repository.OpenKMRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OpenKMServiceTest {
    @Mock
    private OpenKMRepo openKMRepo;
    @Mock
    private Config config;

    @InjectMocks
    private OpenKMService openKMService;

    @Test
    public void testCreateFolder() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());
        when(config.getCreateFolder()).thenReturn("http://example.com");
        when(config.getRootEmployerUUID()).thenReturn("1234");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(con.getInputStream()).thenReturn(inputStream);
        when(con.getResponseCode()).thenReturn(200);
        String result = openKMService.createFolder(path);
        assertNotNull(result);
        assertEquals("successful\n", result);
    }

    @Test
    public void testCreateFolderWhenResponseDifferent() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
        when(config.getCreateFolder()).thenReturn("http://example.com");
        when(config.getRootEmployerUUID()).thenReturn("1234");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(con.getResponseCode()).thenReturn(300);
        String result = openKMService.createFolder(path);
        assertNotNull(result);
    }

    @Test (expected = RuntimeException.class)
    public void testCreateFolderProtocolException() throws IOException {
        HttpURLConnection con = mock(HttpURLConnection.class);
        when(config.getCreateFolder()).thenReturn("http://example.com");
        when(config.getRootEmployerUUID()).thenReturn("1234");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenThrow(ProtocolException.class);
        openKMService.createFolder("/path/to/folder");
        assertTrue(true);
    }

    @Test (expected = RuntimeException.class)
    public void testCreateFolderMalformedURLException() throws IOException {
        HttpURLConnection con = mock(HttpURLConnection.class);
        when(config.getCreateFolder()).thenReturn("http://example.com");
        when(config.getRootEmployerUUID()).thenReturn("1234");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenThrow(MalformedURLException.class);
        openKMService.createFolder("/path/to/folder");
        assertTrue(true);
    }

    @Test (expected = RuntimeException.class)
    public void testCreateFolderIOException() throws IOException {
        HttpURLConnection con = mock(HttpURLConnection.class);
        when(config.getCreateFolder()).thenReturn("http://example.com");
        when(config.getRootEmployerUUID()).thenReturn("1234");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenThrow(IOException.class);
        openKMService.createFolder("/path/to/folder");
        assertTrue(true);
    }

    @Test
    public void testDeleteFolder() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());
        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenReturn(inputStream);
        String result = openKMService.deleteFolder(path);
        assertNotNull(result);
        assertEquals("successful\n", result);
    }

    @Test
    public void testDeleteFolderWhenDifferentStatusCode() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);

        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(300);

        String result = openKMService.deleteFolder(path);
        assertNotNull(result);
    }


    @Test(expected = RuntimeException.class)
    public void testDeleteFolderThrowProtocolException() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);

        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenThrow(ProtocolException.class);
        openKMService.deleteFolder(path);
        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteFolderThrowMalformedException() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);

        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenThrow(MalformedURLException.class);

        openKMService.deleteFolder(path);
        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteFolderThrowIOExceptionException() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);

        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenThrow(IOException.class);
        openKMService.deleteFolder(path);
        assertTrue(true);
    }

    @Test
    public void testDeleteDocument() throws IOException{
        int docId=1;
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());

        when(config.getDeleteDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenReturn(inputStream);

        String result = openKMService.deleteDocument(path);
        assertNotNull(result);
        assertEquals("successful\n", result);
    }

    @Test
    public void testDeleteDocumentWhenDifferentStatusCode() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
//        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(300);

        String result = openKMService.deleteDocument(path);
        assertNotNull(result);
    }


    @Test(expected = RuntimeException.class)
    public void testDeleteDocumentThrowProtocolException() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
//        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenThrow(ProtocolException.class);
        openKMService.deleteDocument(path);
        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteDocumentThrowMalformedException() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
//        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenThrow(MalformedURLException.class);

        openKMService.deleteDocument(path);
        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteDocumentThrowIOExceptionException() throws IOException {
        String path = "/test/folder";
        HttpURLConnection con = mock(HttpURLConnection.class);
//        when(config.getDeleteFolder()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(), Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenThrow(IOException.class);
        openKMService.deleteDocument(path);
        assertTrue(true);
    }

    @Test
    public void testUploadDocument() throws IOException {
        String docPath = "/test/folder/docPath";
        String parentFolderUUID = "/folder/TESTING/";
        HttpURLConnection con = mock(HttpURLConnection.class);
        MultipartFile file = mock(MultipartFile.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());
        InputStream fileReadInside = new ByteArrayInputStream("This is a test.....".getBytes());

        when(config.getUploadDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(con.getInputStream()).thenReturn(inputStream);
        when(file.getInputStream()).thenReturn(fileReadInside);

        String result = openKMService.uploadDocument(file,docPath,parentFolderUUID);
        assertNotNull(result);
        assertEquals("successful\n", result);

    }

    @Test
    public void testUploadDocumentWhenDifferentStatusCode() throws IOException {
        String docPath = "/test/folder/docPath";
        String parentFolderUUID = "/folder/TESTING/";
        HttpURLConnection con = mock(HttpURLConnection.class);
        MultipartFile file = mock(MultipartFile.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());
        InputStream fileReadInside = new ByteArrayInputStream("This is a test.....".getBytes());

        when(config.getUploadDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(300);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
//        when(con.getInputStream()).thenReturn(inputStream);
        when(file.getInputStream()).thenReturn(fileReadInside);

        String result = openKMService.uploadDocument(file,docPath,parentFolderUUID);
        assertNotNull(result);
    }


    @Test(expected = RuntimeException.class)
    public void testUploadDocumentThrowProtocolException() throws IOException {
        String docPath = "/test/folder/docPath";
        String parentFolderUUID = "/folder/TESTING/";
        HttpURLConnection con = mock(HttpURLConnection.class);
        MultipartFile file = mock(MultipartFile.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());
        InputStream fileReadInside = new ByteArrayInputStream("This is a test.....".getBytes());

        when(config.getUploadDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenThrow(ProtocolException.class);


        String result =openKMService.uploadDocument(file,docPath,parentFolderUUID);
        assertNull(result);
        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void testUploadDocumentThrowMalformedException() throws IOException {
        String docPath = "/test/folder/docPath";
        String parentFolderUUID = "/folder/TESTING/";
        HttpURLConnection con = mock(HttpURLConnection.class);
        MultipartFile file = mock(MultipartFile.class);

        when(config.getUploadDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenThrow(MalformedURLException.class);

        openKMService.uploadDocument(file,docPath,parentFolderUUID);
        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void testUploadDocumentThrowIOExceptionException() throws IOException {
        String docPath = "/test/folder/docPath";
        String parentFolderUUID = "/folder/TESTING/";
        HttpURLConnection con = mock(HttpURLConnection.class);
        MultipartFile file = mock(MultipartFile.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());
        InputStream fileReadInside = new ByteArrayInputStream("This is a test.....".getBytes());

        when(config.getUploadDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenThrow(IOException.class);


        String result = openKMService.uploadDocument(file,docPath,parentFolderUUID);
        assertNotNull(result);
    }

    @Test
    public void testGetDocument() throws IOException {
        String docId= "/example";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());

        when(config.getGetDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenReturn(inputStream);

        byte[] result= openKMService.getDocument(docId);

        assertArrayEquals("successful".getBytes(),result);
    }

    @Test
    public void testGetDocumentWithDIfferentStatusCode() throws IOException {
        String docId= "/example";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());

        when(config.getGetDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(300);


        byte[] result= openKMService.getDocument(docId);

        assertNull(result);
    }

    @Test(expected = RuntimeException.class)
    public void testGetDocumentThrowProtocolException() throws IOException {
        String docId= "/example";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());

        when(config.getGetDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenThrow(ProtocolException.class);


        byte[] result= openKMService.getDocument(docId);

        assertNull(result);
    }
    @Test(expected = RuntimeException.class)
    public void testGetDocumentThrowMalformedURLException() throws IOException {
        String docId= "/example";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());

        when(config.getGetDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenThrow(MalformedURLException.class);


        byte[] result= openKMService.getDocument(docId);

        assertNull(result);
    }
    @Test(expected = RuntimeException.class)
    public void testGetDocumentThrowIOException() throws IOException {
        String docId= "/example";
        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("successful".getBytes());

        when(config.getGetDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getResponseCode()).thenThrow(IOException.class);


        byte[] result= openKMService.getDocument(docId);

        assertNull(result);
    }

    @Test
    public void testRenameDocument() throws IOException {
        String docId="ExampleID";
        String newName ="newName.txt";

        HttpURLConnection con = mock(HttpURLConnection.class);
        InputStream inputStream = new ByteArrayInputStream("sucessful".getBytes());

        when(config.getRenameDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(con.getResponseCode()).thenReturn(200);
        when(con.getInputStream()).thenReturn(inputStream);

        String result=  openKMService.renameDocument(docId,newName);

        assertNotNull(result);
        assertEquals("sucessful\n",result);

    }

    @Test
    public void testRenameDocumentWithDifferentStatusCode() throws IOException {
        String docId="ExampleID";
        String newName ="newName.txt";

        HttpURLConnection con = mock(HttpURLConnection.class);


        when(config.getRenameDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(con.getResponseCode()).thenReturn(202);
        String result=  openKMService.renameDocument(docId,newName);

        assertNotNull(result);

    }

    @Test(expected = RuntimeException.class)
    public void testRenameDocumentThrowProtocolException() throws IOException {
        String docId="ExampleID";
        String newName ="newName.txt";

        HttpURLConnection con = mock(HttpURLConnection.class);


        when(config.getRenameDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(con.getResponseCode()).thenThrow(ProtocolException.class);
        openKMService.renameDocument(docId,newName);

        assertTrue(true);
    }

    @Test(expected = RuntimeException.class)
    public void testRenameDocumentThrowMalformedURLException() throws IOException {
        String docId="ExampleID";
        String newName ="newName.txt";

        HttpURLConnection con = mock(HttpURLConnection.class);


        when(config.getRenameDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenReturn(Mockito.mock(OutputStream.class));
        when(con.getResponseCode()).thenThrow(MalformedURLException.class);
        openKMService.renameDocument(docId,newName);

        assertTrue(true);
    }


    @Test(expected = RuntimeException.class)
    public void testRenameDocumentThrowIOException() throws IOException {
        String docId="ExampleID";
        String newName ="newName.txt";

        HttpURLConnection con = mock(HttpURLConnection.class);


        when(config.getRenameDocument()).thenReturn("http://example.com");
        when(openKMRepo.template(Mockito.anyString(),Mockito.anyString())).thenReturn(con);
        when(con.getOutputStream()).thenThrow(IOException.class);

        openKMService.renameDocument(docId,newName);

        assertTrue(true);
    }


}
