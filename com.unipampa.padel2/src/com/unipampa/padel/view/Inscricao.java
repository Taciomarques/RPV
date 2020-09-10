package com.unipampa.padel.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.unipampa.padel.model.Atleta;

public class Inscricao extends JFrame{
	
	private Atleta atleta;

	public static final Dimension DIMENSION = new Dimension(500, 500);

	private JPanel painelPrincipal;
    private JPanel painelConteudo;
    private JLabel nomeLabel;
    private JLabel enderecoLabel;
    private JLabel ruaLabel;
    private JLabel bairroLabel;
    private JLabel cepLabel;
    private JLabel tipoPessoaLabel;
    private JLabel cpf_cnpj;
    private JLabel rg_razaoSocial;
    private JLabel cidadeLabel;
    private JTextField cpf_cnpjText;
    private JTextField rg_razaoSocialText;
    private JTextField nomeText;
    private JTextField ruaText;
    private JTextField bairroText;
    private JTextField cepText;
    private JButton btSalvar;
    private JButton btCancel;
    private JButton novoCliente;
    private JButton tipoCliente;
    private JButton novaCidade;
    private JComboBox selectCidade;
    private JComboBox selectSexo;
    
    public Inscricao(Atleta atleta) throws Exception {
        ImageIcon icon = new ImageIcon("images/biblioteca.PNG");
        setIconImage(icon.getImage());
        setTitle("Cadastrar Cliente");
        this.atleta = atleta;
        setSize(DIMENSION);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        painelPrincipal = new JPanel();
//        painelPrincipal.setBackground(Color.decode("#000000"));
        painelPrincipal.setBackground(Color.gray);
        setContentPane(painelPrincipal);
        btSalvar = new JButton("Salvar");

//        painelPrincipal.setLayout(null);
        painelPrincipal.setLayout(new BorderLayout());
        btSalvar.setLocation(250, 0);
        btSalvar.setSize(100, 20);

        btCancel = new JButton("Cancelar");
        btCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);

            }
        });

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(false);
            }

            @Override
            public void windowOpened(WindowEvent e) {

            }

        });

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());
        jp.setBackground(null);
        jp.add(btSalvar);
        jp.add(btCancel);
        painelPrincipal.add(jp, BorderLayout.SOUTH);

        painelConteudo = new JPanel();
        painelConteudo.setBackground(null);
        painelConteudo.setLocation(0, 0);
        painelConteudo.setSize(200, 400);
        painelConteudo.setLayout(null);
//        painelConteudo.setLayout(new GridLayout(2, 2));

        nomeLabel = new JLabel("Nome:");
        nomeLabel.setSize(100, 20);
        nomeLabel.setLocation(150, 80);

        painelConteudo.add(nomeLabel);

        nomeText = new JTextField();
        nomeText.setSize(100, 20);
        nomeText.setLocation(250, 80);
        painelConteudo.add(nomeText);

        enderecoLabel = new JLabel("Endereço do cliente");
        enderecoLabel.setSize(150, 20);
        enderecoLabel.setLocation(170, 110);

        painelConteudo.add(enderecoLabel);

        ruaLabel = new JLabel("Rua:");
        ruaLabel.setSize(100, 20);
        ruaLabel.setLocation(150, 140);

        painelConteudo.add(ruaLabel);

        ruaText = new JTextField();
        ruaText.setSize(100, 20);
        ruaText.setLocation(250, 140);
        painelConteudo.add(ruaText);

        bairroLabel = new JLabel("Bairro:");
        bairroLabel.setSize(100, 20);
        bairroLabel.setLocation(150, 170);

        painelConteudo.add(bairroLabel);

        bairroText = new JTextField();
        bairroText.setSize(100, 20);
        bairroText.setLocation(250, 170);
        painelConteudo.add(bairroText);

        cepLabel = new JLabel("CEP:");
        cepLabel.setSize(100, 20);
        cepLabel.setLocation(150, 200);

        painelConteudo.add(cepLabel);

        cepText = new JTextField();
        cepText.setSize(100, 20);
        cepText.setLocation(250, 200);
        painelConteudo.add(cepText);

        novaCidade = new JButton("Cadastrar Cidade");
        novaCidade.setSize(150, 20);
        novaCidade.setLocation(280, 230);
        painelConteudo.add(novaCidade);

        novaCidade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Cidade cidade = new Cidade();
//                CadastrarCidade cc = new CadastrarCidade(cidade);
//                cc.setVisible(true);
//                cc.setLocationRelativeTo(null);

            }
        });

        painelPrincipal.add(painelConteudo);

        tipoPessoaLabel = new JLabel("Selecione o tipo:");
        tipoPessoaLabel.setSize(150, 20);
        tipoPessoaLabel.setLocation(220, 250);
        painelConteudo.add(tipoPessoaLabel);

//        ActionListener al = new SalvarCliente(cliente, this);

//        btSalvar.addActionListener(al);

        novoCliente = new JButton("Novo");
        jp.add(novoCliente);

        cpf_cnpj = new JLabel("CPF:");
        cpf_cnpj.setSize(100, 20);
        cpf_cnpj.setLocation(150, 300);
        painelConteudo.add(cpf_cnpj);

        cpf_cnpjText = new JTextField();
        cpf_cnpjText.setSize(100, 20);
        cpf_cnpjText.setLocation(250, 300);
        painelConteudo.add(cpf_cnpjText);

        rg_razaoSocial = new JLabel("RG:");
        rg_razaoSocial.setSize(100, 20);
        rg_razaoSocial.setLocation(150, 330);
        painelConteudo.add(rg_razaoSocial);

        rg_razaoSocialText = new JTextField();
        rg_razaoSocialText.setSize(100, 20);
        rg_razaoSocialText.setLocation(250, 330);
        painelConteudo.add(rg_razaoSocialText);

        String sexos[] = {"Masculino", "Feminino", "Multigênero"};
        selectSexo = new JComboBox(sexos);
        selectSexo.setSize(150, 20);
        selectSexo.setLocation(175, 360);
        painelConteudo.add(selectSexo);

        tipoCliente = new JButton("Física");
        tipoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (tipoCliente.getText().equals("Física")) {

                    tipoCliente.setText("Jurídica");

                    rg_razaoSocial.setText("Razão Social:");

                    cpf_cnpj.setText("CNPJ:");

                    selectSexo.setVisible(false);

                    cpf_cnpjText.setText("");
                    rg_razaoSocialText.setText("");

                } else {
                    tipoCliente.setText("Física");

                    cpf_cnpj.setText("CPF:");

                    rg_razaoSocial.setText("RG:");

                    selectSexo.setVisible(true);

                    cpf_cnpjText.setText("");
                    rg_razaoSocialText.setText("");

                }
            }
        });

        cidadeLabel = new JLabel("Selecione a Cidade:");
        cidadeLabel.setSize(150, 20);
        cidadeLabel.setLocation(280, 230);
        painelConteudo.add(cidadeLabel);

//        BuscarCidades bc = new BuscarCidades();
//        setCidades(bc.retornaCidades());
//        String cidades[] = new String[getCidades().size()];
//
//        int i = 0;
//        for (Cidade c : bc.retornaCidades()) {
//            cidades[i] = c.getNome();
//            i++;
//        }
//
//        selectCidade = new JComboBox(cidades);
//        selectCidade.setSize(150, 20);
//        selectCidade.setLocation(120, 230);
//        painelConteudo.add(selectCidade);

        tipoCliente.setSize(150, 20);
        tipoCliente.setLocation(175, 269);
        painelConteudo.add(tipoCliente);

        novoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                newAtleta();

            }
        }
        );

    }

    public void mensagemCliente() {

//        JOptionPane.showMessageDialog(this, "Atleta " + getAtleta().getNome() + ", cadastrado com sucesso!");
//        setTitle("Editar Atleta");
//        getBtSalvar().setText("Atualizar");

    }

    public void mensagemErro() {

        JOptionPane.showMessageDialog(this, "Erro, tente novamente!");

    }

    public void newAtleta() {

//        getAtleta().setId(0);
//        getAtleta().setNome("");
//        getAtleta().setTipoPessoa(getTipoPessoa());
//        getCliente().getEndereco().setId(0);
//        getCliente().getTipoPessoa().setId(0);
//        getTipoPessoa().setId(0);
//        getCliente().getEndereco().setBairro("");
//        getCliente().getEndereco().setCep("");
//        getCliente().getEndereco().setRua("");
//        getRg_razaoSocialText().setText("");
//        getCpf_cnpjText().setText("");
//        editCliente();

    }

    public void editCliente() {

//        getNomeText().setText(getCliente().getNome());
//        getRuaText().setText(getCliente().getEndereco().getRua());
//        getCepText().setText(getCliente().getEndereco().getCep());
//        getBairroText().setText(getCliente().getEndereco().getBairro());
////        getSenhaText().setText(cliente.getSenha());
//        if (getCliente().getNome() == null || getCliente().getNome().trim().equals("")) {
//            getBtSalvar().setText("Salvar");
//            setTitle("Cadastrar Cliente");
//        } else {
//            getBtSalvar().setText("Atualizar");
//        }

    }

    public void bindCliente() {
        
//        getCliente().setNome(this.getNomeText().getText());
//        getCliente().getEndereco().setBairro(this.getBairroText().getText());
//        getCliente().getEndereco().setCep(this.getCepText().getText());
//        getCliente().getEndereco().setRua(this.getRuaText().getText());
//        int x = 0;
//        for (Cidade c : getCidades()) {
//            if (x == this.selectCidade.getSelectedIndex()) {
//                getCliente().getEndereco().setCidade(c);
//            }
//            x++;
//        }
//        
////        JOptionPane.showMessageDialog(null,this.getSelectTipoBibliotecario().getDebugGraphicsOptions());
//        PessoaFisica pf = new PessoaFisica();
//        PessoaJuridica pj = new PessoaJuridica();
//        if (this.tipoCliente.getText().equals("Física")) {
//            if (this.selectSexo.getSelectedIndex() == 0) {
//                pf.setSexo(Sexo.MASCULINO);
//            } else if (this.selectSexo.getSelectedIndex() == 1) {
//                pf.setSexo(Sexo.FEMININO);
//            } else {
//                pf.setSexo(Sexo.MULTIGENERO);
//            }
//            pf.setCpf(this.cpf_cnpjText.getText());
//            pf.setRg(this.rg_razaoSocialText.getText());
//            setTipoPessoa(pf);
//
//        } else {
//            pj.setCnpj(this.cpf_cnpjText.getText());
//            pj.setRazaoSocial(this.rg_razaoSocialText.getText());
//            setTipoPessoa(pj);
//
//        }
//        getCliente().setTipoPessoa(getTipoPessoa());
    }

    public static void main(String args[]) {

        try {

            Atleta atleta = new Atleta();
//            atleta.setNome("adm");

            Inscricao i = new Inscricao(atleta);
            i.setResizable(false);
            i.setLocationRelativeTo(null);
            i.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
}
