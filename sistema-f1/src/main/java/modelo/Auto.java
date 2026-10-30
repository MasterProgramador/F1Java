package modelo;

public class Auto {
    private int idAuto;
    private String modelo;
    private int anioFabricacion;
    private int idEscuderia;

    public Auto() {}

    public Auto(int idAuto, String modelo, int anioFabricacion, int idEscuderia) {
        this.idAuto = idAuto;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.idEscuderia = idEscuderia;
    }

    public int getIdAuto() {
    	return idAuto;
    }
    public void setIdAuto(int idAuto) {
    	this.idAuto = idAuto;
    }

    public String getModelo() {
    	return modelo;
    }
    public void setModelo(String modelo) {
    	this.modelo = modelo;
    }

    public int getAnioFabricacion() {
    	return anioFabricacion;
    }
    public void setAnioFabricacion(int anioFabricacion) {
    	this.anioFabricacion = anioFabricacion;
    }

    public int getIdEscuderia() {
    	return idEscuderia;
    }
    public void setIdEscuderia(int idEscuderia) {
    	this.idEscuderia = idEscuderia;
    }
}