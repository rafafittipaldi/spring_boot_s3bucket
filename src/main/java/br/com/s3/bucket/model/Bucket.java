package br.com.s3.bucket.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Classe Modelo Bucket
 * @author Rafael Fittipaldi
 */
public class Bucket implements Serializable {

	private static final long serialVersionUID = 8156686764884455698L;
	
	private String pathS3;
	private String key;
	private String tag;
	private String mensagem;
	
	public Bucket() {}
	
	public Bucket(String pathS3, String key, String tag, String mensagem) {
		this.pathS3   = pathS3;
		this.key      = key;
		this.tag      = tag;
		this.mensagem = mensagem;
	}
	
	public String getPathS3() {
		return pathS3;
	}
	
	public void setPathS3(String pathS3) {
		this.pathS3 = pathS3;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
