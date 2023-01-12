package br.com.listadetarefas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import br.com.dao.GenericDao;
import br.com.enums.PrioridadeEnum;
import br.com.model.Responsavel;
import br.com.model.Tarefa;
import lombok.Data;

@ManagedBean(name = "TarefaBean")
@ViewScoped
@Data
public class TarefaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idResponsavelSelecionado;

	private Tarefa tarefa = new Tarefa();
	private static Tarefa tarefaParam;

	private String teste;

	private List<Responsavel> responsaveis = new ArrayList<>();
	private List<Tarefa> tarefas = new ArrayList<>();

	private GenericDao<Tarefa> tarefaDao = new GenericDao<>();
	private GenericDao<Responsavel> responsavelDao = new GenericDao<>();

	private boolean modoEdicao = false;
	private String situacao = "";

	public PrioridadeEnum[] getPrioridades() {
		return PrioridadeEnum.values();
	}

	public String salvar() {

		Optional<Responsavel> resp = buscarResponsavelPorId();

		if (resp.isPresent()) {
			this.tarefa.setResponsavel(resp.get());
		}

		this.tarefa.setConcluida(false);

		this.tarefaDao.salvar(tarefa);
		this.tarefa = new Tarefa();

		if (modoEdicao) {
			this.modoEdicao = false;
			this.tarefa = new Tarefa();
			tarefaParam = null;
			return "tarefas?faces-redirect=true";
		}
		return "";
	}

	private Optional<Responsavel> buscarResponsavelPorId() {
		return responsaveis.stream().filter(a -> a.getId() == this.idResponsavelSelecionado).findFirst();
	}

	public String listar() {
		this.tarefa = new Tarefa();
		this.modoEdicao = false;

		this.carregarTarefas();

		return "tarefas?faces-redirect=true";
	}

	public String editar() {
		return "cadastro?faces-redirect=true";
	}

	public String deletar() {
		this.tarefaDao.deletarPorId(this.tarefa);
		this.tarefas.remove(this.tarefa);
		this.tarefa = new Tarefa();
		return "";
	}

	public String concluirTarefa() {
		this.tarefa.setConcluida(true);

		this.tarefaDao.salvar(this.tarefa);
		this.tarefas.remove(this.tarefa);
		this.tarefa = new Tarefa();
		return "";
	}

	@PostConstruct
	public void carregarResponsaveis() {

		this.responsaveis = this.responsavelDao.listar(Responsavel.class);
		this.carregarTarefas();

		if (!modoEdicao) {
			this.tarefa = new Tarefa();
			this.initTarefa();
			this.tarefa.setPrioridadeEnum(PrioridadeEnum.ALTA);
		}

		if (tarefaParam != null) {
			this.modoEdicao = true;
			this.tarefa = tarefaParam;
			tarefaParam = null;
		} else {
			this.tarefa = new Tarefa();
		}

	}

	public void carregarTarefas() {
		List<Tarefa> tarefas = this.tarefaDao.listar(Tarefa.class);
		for (Tarefa t : tarefas) {
			if (!t.getConcluida()) {
				this.tarefas.add(t);
			}
		}
	}

	public void listenerTarefa(ActionEvent event) {
		tarefaParam = (Tarefa) event.getComponent().getAttributes().get("tarefa");
	}

	private void initTarefa() {
		Optional<Responsavel> resp = buscarResponsavelPorId();
		if (resp.isPresent()) {
			this.tarefa.setResponsavel(resp.get());
		}

		if (this.situacao.toLowerCase().equals("emandamento")) {
			this.tarefa.setConcluida(false);
		} else {
			this.tarefa.setConcluida(true);
		}
	}

	public void filtrar() {
		this.initTarefa();
		
		String query = "SELECT * FROM tarefa WHERE 1 = 1";

		if (tarefa.getId() != null) {
			query += " AND id = " + tarefa.getId();
		}

		if (tarefa.getTitulo() != null && !tarefa.getTitulo().isEmpty()) {
			query += " AND titulo like '%" + tarefa.getTitulo().trim() + "%' OR descricao LIKE '%"
					+ this.tarefa.getTitulo().trim() + "%'";
		}

		if (tarefa.getResponsavel() != null) {
			query += " AND responsavel_id = " + tarefa.getResponsavel().getId();
		}

		if (tarefa.getConcluida() != null) {
			query += " AND concluida = " + tarefa.getConcluida();
		}
		
		query += " ORDER BY id";

		List<Tarefa> tarefasFiltradas = this.tarefaDao.queryPersonalizada(query, Tarefa.class);
		this.tarefas = tarefasFiltradas;
	}

	public void limparFiltro() {
		this.tarefas.clear();
		this.carregarTarefas();
		this.tarefa = new Tarefa();
		this.situacao = "emAndamento";
		this.initTarefa();
	}

}
