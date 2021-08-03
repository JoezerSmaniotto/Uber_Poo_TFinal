package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Veiculo {
	private Long id;
	private String tipo;
	private String placa;
	private Calendar anoFabricacao;
	private Motorista motorista;
	private Boolean situacao;
	private Boolean situacaoOcupado;
	
	public Veiculo() {
		super();
	}

	/*public Veiculo(Long id, String tipo, String placa, Calendar anoFabricacao, Boolean situacao,
			Boolean situacaoOcupado) {*/
	public Veiculo(Long id, String tipo, String placa, Boolean situacao,Boolean situacaoOcupado) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.placa = placa;
		//this.anoFabricacao = anoFabricacao;
		this.situacao = situacao;
		this.situacaoOcupado = situacaoOcupado;
	}
	// Sem ID
	public Veiculo(String tipo, String placa, Boolean situacao,Boolean situacaoOcupado) {
		super();
		this.tipo = tipo;
		this.placa = placa;
		//this.anoFabricacao = anoFabricacao;
		this.situacao = situacao;
		this.situacaoOcupado = situacaoOcupado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Calendar getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(Calendar anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Boolean getSituacao() {
		return situacao;
	}

	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}

	public Boolean getSituacaoOcupado() {
		return situacaoOcupado;
	}

	public void setSituacaoOcupado(Boolean situacaoOcupado) {
		this.situacaoOcupado = situacaoOcupado;
	}

	@Override
	public String toString() {
		return "\nVeiculo [id=" + id + ", tipo=" + tipo + ", placa=" + placa + ", anoFabricacao=" + calendarToString(anoFabricacao)
				+ ", situacao=" + situacao + ", situacaoOcupado=" + situacaoOcupado + "]";
	}
	
	private static String calendarToString(Calendar data) {
		if(data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
			return sdf.format(data.getTime());
		}
		return "00/00/0000";
	}

		
	
}
