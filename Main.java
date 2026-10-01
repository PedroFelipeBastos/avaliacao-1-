import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Mecanico> mecanicos = new ArrayList<>();
    static ArrayList<Box> boxes = new ArrayList<>();
    static ArrayList<OrdemServico> ordens = new ArrayList<>();

    public static void main(String[] args) {
        criarDados();
        int opcao;
        do {
            System.out.println("\n1 Cadastrar ordem de servico");
            System.out.println("2 Associar mecanico a box");
            System.out.println("3 Atribuir ordem a box");
            System.out.println("4 Ordens de um box");
            System.out.println("5 Finalizadas por box");
            System.out.println("6 Buscar por status");
            System.out.println("7 Detalhes de uma ordem");
            System.out.println("0 Sair");
            System.out.print("Opcao: ");
            opcao = Integer.parseInt(sc.nextLine());

            if (opcao == 1) {
                cadastrarOrdem();
            } else if (opcao == 2) {
                associarMecanico();
            } else if (opcao == 3) {
                atribuirOrdem();
            } else if (opcao == 4) {
                ordensDoBox();
            } else if (opcao == 5) {
                finalizadasPorBox();
            } else if (opcao == 6) {
                buscarPorStatus();
            } else if (opcao == 7) {
                detalhesOrdem();
            }
        } while (opcao != 0);
    }

    static void criarDados() {
        mecanicos.add(new Mecanico("Pedro", "111.111.111-11", "Mecanica", "3199999-0001"));
        mecanicos.add(new Mecanico("Glender", "222.222.222-22", "Eletrica", "3199999-0002"));
        mecanicos.add(new Mecanico("Gabriel", "333.333.333-33", "Funilaria", "3199999-0003"));

        boxes.add(new Box(1, "Mecanica", 2, "Setor A"));
        boxes.add(new Box(2, "Eletrica", 3, "Setor B"));
        boxes.add(new Box(3, "Funilaria", 2, "Setor C"));
    }

    static Box buscarBox(int numero) {
        for (Box b : boxes) {
            if (b.getNumero() == numero) {
                return b;
            }
        }
        return null;
    }

    static OrdemServico buscarOrdem(int codigo) {
        for (OrdemServico o : ordens) {
            if (o.getCodigo() == codigo) {
                return o;
            }
        }
        return null;
    }

    static Box pedirBox() {
        for (Box b : boxes) {
            System.out.println(b);
        }
        System.out.print("Numero do box: ");
        Box box = buscarBox(Integer.parseInt(sc.nextLine()));
        if (box == null) {
            System.out.println("Box nao encontrado");
        }
        return box;
    }

    static String nomeStatus(int numero) {
        if (numero == 1) {
            return "aberta";
        } else if (numero == 2) {
            return "em execucao";
        }
        return "finalizada";
    }

    static boolean atribuir(OrdemServico ordem, Box box) {
        String erro = box.motivoRecusa(ordem.getServico());
        if (erro != null) {
            System.out.println(erro);
            return false;
        }
        ordem.atribuirBox(box);
        return true;
    }

    static void cadastrarOrdem() {
        System.out.print("Codigo: ");
        int codigo = Integer.parseInt(sc.nextLine());
        if (buscarOrdem(codigo) != null) {
            System.out.println("Ja existe ordem com esse codigo");
            return;
        }
        System.out.print("Cliente: ");
        String cliente = sc.nextLine();
        System.out.print("Modelo do veiculo: ");
        String modelo = sc.nextLine();
        System.out.print("Placa: ");
        String placa = sc.nextLine();
        System.out.print("Data: ");
        String data = sc.nextLine();
        System.out.print("Nome do servico: ");
        String nomeServico = sc.nextLine();
        System.out.print("Tempo estimado (min): ");
        int tempo = Integer.parseInt(sc.nextLine());
        System.out.print("Valor: ");
        double valor = Double.parseDouble(sc.nextLine());
        System.out.print("Categoria (Mecanica, Eletrica, Funilaria): ");
        String categoria = sc.nextLine();
        System.out.print("Status (1 - aberta, 2 - em execucao, 3 - finalizada): ");
        String status = nomeStatus(Integer.parseInt(sc.nextLine()));

        Servico servico = new Servico(nomeServico, tempo, valor, categoria);
        OrdemServico ordem = new OrdemServico(codigo, cliente, modelo, placa, data, servico);

        if (!status.equals("aberta")) {
            Box box = pedirBox();
            if (box == null || !atribuir(ordem, box)) {
                System.out.println("Ordem nao cadastrada");
                return;
            }
            if (status.equals("finalizada")) {
                ordem.finalizar();
            }
        }
        ordens.add(ordem);
        System.out.println("Ordem cadastrada");
    }

    static void associarMecanico() {
        Box box = pedirBox();
        if (box == null) {
            return;
        }
        for (int i = 0; i < mecanicos.size(); i++) {
            System.out.println((i + 1) + " - " + mecanicos.get(i));
        }
        System.out.print("Mecanico: ");
        int escolha = Integer.parseInt(sc.nextLine());
        if (escolha < 1 || escolha > mecanicos.size()) {
            System.out.println("Mecanico invalido");
            return;
        }
        Mecanico mecanico = mecanicos.get(escolha - 1);
        for (Box b : boxes) {
            if (b.getMecanico() == mecanico) {
                System.out.println("Esse mecanico ja e responsavel por um box");
                return;
            }
        }
        box.setMecanico(mecanico);
        System.out.println("Mecanico associado");
    }

    static void atribuirOrdem() {
        System.out.print("Codigo da ordem: ");
        OrdemServico ordem = buscarOrdem(Integer.parseInt(sc.nextLine()));
        if (ordem == null) {
            System.out.println("Ordem nao encontrada");
            return;
        }
        if (!ordem.getStatus().equals("aberta")) {
            System.out.println("So ordens abertas podem ser atribuidas");
            return;
        }
        Box box = pedirBox();
        if (box != null && atribuir(ordem, box)) {
            System.out.println("Ordem atribuida");
        }
    }

    static void ordensDoBox() {
        Box box = pedirBox();
        if (box == null) {
            return;
        }
        int total = 0;
        for (OrdemServico o : ordens) {
            if (o.getBox() == box) {
                System.out.println("\n" + o.detalhes());
                total++;
            }
        }
        System.out.println("\nTotal de ordens: " + total);
    }

    static void finalizadasPorBox() {
        for (Box b : boxes) {
            int total = 0;
            for (OrdemServico o : ordens) {
                if (o.getBox() == b && o.getStatus().equals("finalizada")) {
                    total++;
                }
            }
            System.out.println("Box " + b.getNumero() + ": " + total);
        }
    }

    static void buscarPorStatus() {
        System.out.print("Status (1 - aberta, 2 - em execucao, 3 - finalizada): ");
        String status = nomeStatus(Integer.parseInt(sc.nextLine()));
        for (OrdemServico o : ordens) {
            if (o.getStatus().equals(status)) {
                System.out.println("\n" + o.detalhes());
            }
        }
    }

    static void detalhesOrdem() {
        System.out.print("Codigo da ordem: ");
        OrdemServico ordem = buscarOrdem(Integer.parseInt(sc.nextLine()));
        if (ordem == null) {
            System.out.println("Ordem nao encontrada");
        } else {
            System.out.println("\n" + ordem.detalhes());
        }
    }
}
