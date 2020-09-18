package br.com.s3.bucket.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * Classe de Utilitário
 * @author Rafael Fittipaldi
 */
public class Util {

	/**
	 * Converte um Multipart em Arquivo
	 * @param file {@link MultipartFile}
	 * @return {@link File}
	 * @throws IOException
	 */
    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File             convFile = new File(file.getOriginalFilename());
        FileOutputStream fos      = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    
    /**
     * Gera um nome de arquivo
     * @param multiPart {@link MultipartFile}
     * @return Nome do Arquivo
     */
    public static String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}
