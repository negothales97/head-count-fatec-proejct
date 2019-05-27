package view.cargo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.CargoController;
import model.dao.CargoDAO;
import model.vo.Cargo;

public class ListaCargo extends JFrame implements ActionListener {
	private CargoController control;
	private CargoDAO dao;
	private JFrame janela;
	private JPanel contentPanel;
	private JPanel panelGrid;
	private Container container;
	private BorderLayout boderLayout;
	private GridBagLayout gbLayout;
	
	private JButton btnNovo;
	private JButton btnSair;
	private JButton btnRemover;
	private JButton btnPesquisar;
	private JTextField txtPesquisar;

	private JTable tblCargo;


	public ListaCargo() throws SQLException {
		String[] colunas = { "Codigo", "Cargo" };
		dao = new CargoDAO();

		List<Cargo> filiais = dao.read();
		Object[][] dados = new Object[filiais.size()][4];
		for (int i = 0; i < filiais.size(); i++) {
			Cargo cargo = filiais.get(i);
			dados[i][0] = cargo.getId();
			dados[i][1] = cargo.getNome();
		}

		janela = new JFrame();
		contentPanel = new JPanel();
		panelGrid = new JPanel();
		container = new JPanel();

		boderLayout = new BorderLayout();
		gbLayout = new GridBagLayout();

		panelGrid.setLayout(gbLayout);
		contentPanel.setLayout(boderLayout);
		container.setLayout(new FlowLayout());


		btnNovo = new JButton("Novo");
		btnRemover = new JButton("Remover");
		btnSair = new JButton("Sair");
		btnPesquisar = new JButton("Pesquisar");

		txtPesquisar = new JTextField(10);

		tblCargo = new JTable(dados, colunas);
		tblCargo.setSize(container.getWidth(), container.getHeight());

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(5, 5, 5, 5);

		panelGrid.add(btnNovo, gbc);
		panelGrid.add(btnSair, gbc);
		panelGrid.add(btnRemover, gbc);
		panelGrid.add(txtPesquisar, gbc);
		panelGrid.add(btnPesquisar, gbc);
		container.add(tblCargo, gbc);

		contentPanel.add(BorderLayout.NORTH, panelGrid);
		contentPanel.add(BorderLayout.CENTER, container);

		btnNovo.addActionListener(this);
		btnRemover.addActionListener(this);
		btnSair.addActionListener(this);
		btnPesquisar.addActionListener(this);

		janela.setContentPane(contentPanel);
		janela.setTitle("Lista de Cargos");
		janela.setSize(600, 400);
		janela.setVisible(true);
		janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object fonte = e.getSource();
		control = new CargoController();

		if (fonte == btnNovo) {
			control.novoCargo();
			janela.dispose();

		}
		if (fonte == btnRemover) {

			int id = Integer.parseInt(JOptionPane.showInputDialog("Informe o codigo a ser removido"));
			try {
				control.deletaCargo(id);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			janela.dispose();
			JOptionPane.showMessageDialog(null, "Cargo removido com sucesso");
		}
		if (fonte == btnSair) {
			janela.dispose();
		}
		if (fonte == btnPesquisar) {
			txtPesquisar.getText();
		}

	}
}