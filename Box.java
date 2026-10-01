public class Box {
    private int numero;
    private String tipoServico;
    private int capacidadeMaxima;
    private String localizacao;
    private Mecanico mecanico;
    private int ocupacao;

    public Box(int numero, String tipoServico, int capacidadeMaxima, String localizacao) {
        this.numero = numero;
        this.tipoServico = tipoServico;
        this.capacidadeMaxima = capacidadeMaxima;
        this.localizacao = localizacao;
        this.ocupacao = 0;
    }

    public int getNumero() {
        return numero;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public String motivoRecusa(Servico servico) {
        if (mecanico == null) {
            return "O box nao tem mecanico responsavel";
        }
        if (!tipoServico.equalsIgnoreCase(servico.getCategoria())) {
            return "Esse tipo de servico nao e permitido neste box";
        }
        if (ocupacao >= capacidadeMaxima) {
            return "O box esta na capacidade maxima";
        }
        return null;
    }

    public void ocupar() {
        ocupacao++;
    }

    public void liberar() {
        ocupacao--;
    }

    public String toString() {
        String texto = "Box " + numero + " - " + tipoServico + " - " + localizacao;
        if (mecanico != null) {
            texto += " - Mecanico: " + mecanico.getNome();
        }
        return texto;
    }
}
