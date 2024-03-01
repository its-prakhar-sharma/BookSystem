package org.cnss.service;

import lombok.extern.slf4j.Slf4j;
import org.cnss.Config;
import org.cnss.repository.OpenKMRepo;
import org.cnss.utils.ConstantsOpenKM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class OpenKMService {

    @Autowired
    private OpenKMRepo openKMRepo;

    @Autowired
    private Config config;

    public String createFolder(String path) {
        String stringOutput = "";
        try {
            HttpURLConnection con = openKMRepo.template(config.getCreateFolder() + config.getRootEmployerUUID(),
                    HttpMethod.POST + "");
            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, ConstantsOpenKM.APPLICATION_JSON);
            con.setDoOutput(true); // to set request body
            con.setRequestProperty(ConstantsOpenKM.ACCEPT, ConstantsOpenKM.APPLICATION_JSON);
            OutputStream output = con.getOutputStream();
            output.write((path).getBytes());
            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    stringOutput = stringOutput + temp + "\n";
                }
            } else {
                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
            }
            con.disconnect();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringOutput;
    }

    public String deleteFolder(String fileNumber) {
        String stringOutput = "";
        try {
            HttpURLConnection con = openKMRepo.template(config.getDeleteFolder() + fileNumber,
                    HttpMethod.DELETE.name());
            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, ConstantsOpenKM.APPLICATION_JSON);
            con.setRequestProperty(ConstantsOpenKM.ACCEPT, ConstantsOpenKM.APPLICATION_JSON);

            if (con.getResponseCode() == 200 || con.getResponseCode() == 204) {
                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    stringOutput = stringOutput + temp + "\n";
                }
            } else {
                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
            }
            con.disconnect();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringOutput;
    }

    //    public String oldCreateFolder(String path) {
//        String stringOutput = "";
//        try {
//            HttpURLConnection con = openKMRepo.template(config.getCreateFolder(), HttpMethod.POST + "");
//            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, ConstantsOpenKM.APPLICATION_JSON);
//            con.setDoOutput(true); // to set request body
//            con.setRequestProperty(ConstantsOpenKM.ACCEPT, ConstantsOpenKM.APPLICATION_JSON);
//
//            OutputStream output = con.getOutputStream();
//            output.write((config.getRootPath() + path).getBytes());
//
//            if (con.getResponseCode() == 200) {
//                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
//                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
//                String temp = "";
//                while ((temp = br.readLine()) != null) {
//                    stringOutput = stringOutput + temp + "\n";
//                }
//            } else {
//                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
//            }
//            con.disconnect();
//        } catch (ProtocolException e) {
//            throw new RuntimeException(e);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return stringOutput;
//    }

//    public String olduploadDocument(MultipartFile file, String docPath) {
//        String stringOutput = "";
//        try {
//            // Read the file content
//            byte[] fileBytes = file.getBytes();
//
//            HttpURLConnection con = openKMRepo.template(config.getUploadDocument(), HttpMethod.POST.name());
//            con.setRequestProperty(ConstantsOpenKM.ACCEPT, ConstantsOpenKM.APPLICATION_JSON);
//            con.setDoOutput(true);
//
//            // Set up the request body
//            String boundary = Long.toHexString(System.currentTimeMillis()); // random boundary
//            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, "multipart/form-data; boundary=" + boundary);
// this is in the headers
//            try (OutputStream output = con.getOutputStream()) {
//                output.write(("--" + boundary + "\r\n").getBytes());
//                output.write(("Content-Disposition: form-data; name=\"docPath\"\r\n\r\n" + docPath + "\r\n")
//                .getBytes());
//                output.write(("--" + boundary + "\r\n").getBytes());
//                output.write(("Content-Disposition: form-data; name=\"content\"; filename=\"" + file.getName() +
//                "\"\r\n").getBytes());
//                output.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());
//                output.write(fileBytes);
//                output.write(("\r\n--" + boundary + "--\r\n").getBytes());
//            }
//
//            if (con.getResponseCode() == 200) {
//                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
//                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
//                String temp = "";
//                while ((temp = br.readLine()) != null) {
//                    stringOutput = stringOutput + temp + "\n";
//                }
//            } else {
//                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
//            }
//            con.disconnect();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            return stringOutput;
//        }
//    }

    public String uploadDocument(MultipartFile file, String docPath, String parentFolderUUID) {
        String stringOutput = "";
        try {
            // Read the file content
//            byte[] fileBytes = file.getBytes();

            HttpURLConnection con = openKMRepo.template(config.getUploadDocument() + "/" + parentFolderUUID,
                    HttpMethod.POST.name());
            con.setRequestProperty(ConstantsOpenKM.ACCEPT, ConstantsOpenKM.APPLICATION_JSON);
            con.setDoOutput(true);

            // Set up the request body
            String boundary = Long.toHexString(System.currentTimeMillis()); // random boundary
            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, "multipart/form-data; boundary=" + boundary); //this
            // is in the headers
            try (OutputStream output = con.getOutputStream()) {
                String boundaryAppended = "--" + boundary;
                String lineBreak = "\r\n";
                output.write(("Content-Disposition: form-data; name=\"autoUpdate\"" + lineBreak + lineBreak + "true" + lineBreak).getBytes());
                output.write((boundaryAppended + lineBreak).getBytes());
                output.write(("Content-Disposition: form-data; name=\"comment\"" + lineBreak + lineBreak + "sample" + lineBreak).getBytes());
                output.write((boundaryAppended + lineBreak).getBytes());
                output.write(("Content-Disposition: form-data; name=\"createMissingFolders\"" + lineBreak + lineBreak + "false" + lineBreak).getBytes());
                output.write((boundaryAppended + lineBreak).getBytes());
                output.write(("Content-Disposition: form-data; name=\"increment\"" + lineBreak + lineBreak + "0" + lineBreak).getBytes());
                output.write((boundaryAppended + lineBreak).getBytes());
                output.write(("Content-Disposition: form-data; name=\"nodeClass\"" + lineBreak + lineBreak + "0" + lineBreak).getBytes());
                output.write((boundaryAppended + lineBreak).getBytes());

                //output.write(("Content-Disposition: form-data; name=\"docPath\"\r\n\r\n" + docPath + "\r\n")
                // .getBytes());
                //output.write(("--" + boundary + "\r\n").getBytes());
                output.write(("Content-Disposition: form-data; name=\"content\"; filename=\"" + docPath + "\"\r\n").getBytes());
                output.write(("Content-Type: application/octet-stream\r\n\r\n").getBytes());
                //output.write(fileBytes);
                //output.write(("Content-Type: application/pdf" + lineBreak + lineBreak).getBytes());
//            File fileWrite = new File(filePath);
                InputStream fileInputStream = file.getInputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                fileInputStream.close();
                output.write((lineBreak + boundaryAppended + "--" + lineBreak).getBytes());
                output.flush();
            }

            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    stringOutput = stringOutput + temp + "\n";
                }
            } else {
                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode() + " " + con.getResponseMessage());
            }
            con.disconnect();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringOutput;
    }

    public String deleteDocument(String docId) {
        String stringOutput = "";
        try {
            HttpURLConnection con = openKMRepo.template(config.getDeleteDocument() + docId, HttpMethod.DELETE.name());
            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, ConstantsOpenKM.APPLICATION_JSON);
            con.setRequestProperty(ConstantsOpenKM.ACCEPT, ConstantsOpenKM.APPLICATION_JSON);

            if (con.getResponseCode() == 200 || con.getResponseCode() == 204) {
                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
                String temp = "";
                while ((temp = br.readLine()) != null) {
                    stringOutput = stringOutput + temp + "\n";
                }
            } else {
                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
            }
            con.disconnect();
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringOutput;
    }

    public byte[] getDocument(String docId) {
        try {
//            HttpURLConnection con = openKMRepo.template(config.getGetDocument() + docId, HttpMethod.GET.name());
            String appendURL = config.getGetDocument().replace("docId", docId);
            HttpURLConnection con = openKMRepo.template(appendURL, HttpMethod.GET.name());
            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, ConstantsOpenKM.APPLICATION_JSON);

            con.setRequestMethod(HttpMethod.GET.name());
            con.setRequestProperty(ConstantsOpenKM.ACCEPT, ConstantsOpenKM.APPLICATION_OCTET_STREAM);

            if (con.getResponseCode() == 200) {
                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
                InputStream inputStream = con.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                inputStream.close();
                outputStream.close();
                con.disconnect();
                return outputStream.toByteArray();
            } else {
                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public String renameDocument(String documentId, String newName) {
        String stringOutput = "";
        try {
            String appendURL = config.getRenameDocument().replace("{documentId}", documentId);
            HttpURLConnection con = openKMRepo.template(appendURL, HttpMethod.PUT.name());
            con.setRequestProperty(ConstantsOpenKM.CONTENT_TYPE, ConstantsOpenKM.APPLICATION_FORM);
            con.setDoOutput(true);
            String urlEncodedData = "newName=" + URLEncoder.encode(newName, StandardCharsets.UTF_8.name());
            OutputStream output = con.getOutputStream();
            output.write(urlEncodedData.getBytes());

            if (con.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
                String temp;
                while ((temp = br.readLine()) != null) {
                    stringOutput += temp + "\n";
                }
                br.close();
                log.info("Successfully renamed document.");
            } else {
                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
            }
            con.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringOutput;
    }

//    public String sendEmailWithAttachment(String from, String to, String subject, String pdfFilePath) {
//        String stringOutput = "";
//        try {
//            String appendURL = (config.getMails()+"?from=" + from + "&subject=" + subject + "&to=" + to);
//            HttpURLConnection con = openKMRepo.template(appendURL, HttpMethod.POST.name());
//
//            con.setRequestProperty(ConstantsOpenKM.ACCEPT, "*/*");
//
//            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//            builder.addBinaryBody("content", new File(pdfFilePath), ContentType.create("application/pdf"), "sample
//            .pdf");
//
//            org.apache.http.HttpEntity entity = builder.build();
//            con.setRequestProperty("Content-Type", entity.getContentType().getValue());
//
//            con.setDoOutput(true);
//            OutputStream output = con.getOutputStream();
//            entity.writeTo(output);
//            output.flush();
//
//            if (con.getResponseCode() == 200) {
//                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
//                log.info(ConstantsOpenKM.OPENKM_SUCCESS);
//                String temp = "";
//                while ((temp = br.readLine()) != null) {
//                    stringOutput = stringOutput + temp + "\n";
//                }
//            } else {
//                log.error(ConstantsOpenKM.OPENKM_FAILURE + con.getResponseCode());
//            }
//            con.disconnect();
//        } catch (ProtocolException e) {
//            throw new RuntimeException(e);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return stringOutput;
//    }
}