package br.com.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.apache.el.parser.AstGreaterThan;
import org.junit.Test;

import br.com.dao.GenericDao;
import br.com.enums.PrioridadeEnum;
import br.com.model.Responsavel;
import br.com.model.Tarefa;

public class DaoTest {
	
	private GenericDao<Tarefa> dao = new GenericDao<>();

//	@Test
//	public void salvarEretornarUsuario() {
//		Tarefa tarefa = new Tarefa(
//				null,
//				"Título teste",
//				"Descrição teste",
//				new Responsavel(Long.valueOf(1), "José"),
//				PrioridadeEnum.ALTA,
//				LocalDate.now(),
//				false
//				);
//		Tarefa t = this.dao.salvar(tarefa);
//		assertNotNull(t);
//	}
	
//	@Test
//	public void buscarEretornarUsuarioPorId() {
//		
//		Tarefa t = this.dao.buscarPorId(Long.valueOf(5));
//		assertNotNull(t);
//		assertEquals(t.getId(), Long.valueOf(5));
//	}
	
//	@Test
//	public void deletarTarefaSemLancarExcecao() {
//		Tarefa tarefa = this.dao.buscarPorId(Long.valueOf(4));
//		assertNotNull(tarefa);
//		this.dao.deletarPorId(tarefa);
//	}
	
	@Test
	public void retornarUmaListaDeTarefas() {
		List<Tarefa> tarefas = this.dao.listar(Tarefa.class);
		assertNotNull(tarefas);
		assertTrue(tarefas.size() > 0);
	}
	
}














