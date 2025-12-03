// src/main/java/br/unitins/tp1/dto/mercadopago/MercadoPagoPixResponseDTO.java
package br.unitins.tp1.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MercadoPagoPixResponseDTO {
    private String id;
    private String status;
    @JsonProperty("status_detail")
    private String statusDetail; // Added statusDetail field
    @JsonProperty("point_of_interaction")
    private PointOfInteractionDTO pointOfInteraction;

    // Getters
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDetail() { // Added getter for statusDetail
        return statusDetail;
    }

    public PointOfInteractionDTO getPointOfInteraction() {
        return pointOfInteraction;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusDetail(String statusDetail) { // Added setter for statusDetail
        this.statusDetail = statusDetail;
    }

    public void setPointOfInteraction(PointOfInteractionDTO pointOfInteraction) {
        this.pointOfInteraction = pointOfInteraction;
    }
}