public class OrdemServico {
    private int codigo;
    private String cliente;
    private String modelo;
    private String placa;
    private String data;
    private String status;
    private double valorEstimado;
    private Servico servico;
    private Box box;

    public OrdemServico(int codigo, String cliente, String modelo, String placa,
            String data, Servico servico) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.modelo = modelo;
        this.placa = placa;
        this.data = data;
        this.servico = servico;
        this.valorEstimado = servico.getValor();
        this.status = "aberta";
    }

    public int getCodigo() {
        return codigo;
    }

    public String getStatus() {
        return status;
    }

    public Servico getServico() {
        return servico;
    }

    public Box getBox() {
        return box;
    }

    public void atribuirBox(Box box) {
        this.box = box;
        this.status = "em execucao";
        box.ocupar();
    }

    public void finalizar() {
        this.status = "finalizada";
        box.liberar();
    }

    public String detalhes() {
        String texto = "Codigo: " + codigo
                + "\nCliente: " + cliente
                + "\nVeiculo: " + modelo + " - Placa: " + placa
                + "\nData: " + data
                + "\nStatus: " + status
                + "\nValor estimado: R$ " + valorEstimado
                + "\nServico: " + servico.getNome() + " (" + servico.getCategoria() + ")"
                + " - " + servico.getTempoEstimado() + " min";
        if (box != null) {
            texto += "\nBox: " + box.getNumero() + " - " + box.getLocalizacao()
                    + "\nMecanico: " + box.getMecanico().getNome();
        }
        return texto;
    }
}
