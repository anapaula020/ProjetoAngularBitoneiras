package mssaat.org.form;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;

public class ImageForm {
    

    @FormParam("imageUrl")
    @PartType(MediaType.TEXT_PLAIN)
    private String imageUrl;

    @FormParam("imagem")
    @PartType("application/octet-stream")
    private byte[] imagem;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImagem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}