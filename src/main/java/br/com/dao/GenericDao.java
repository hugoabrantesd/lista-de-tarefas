package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import br.com.jpautil.JPAUtil;
import br.com.model.Tarefa;

public class GenericDao<T> {

	private static EntityManager entityManager;

	private static void init() {
		entityManager = JPAUtil.getEntityManager();
		entityManager.getTransaction().begin();
	}

	private static void commitAndClose() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public T salvar(T entidade) {
		init();

		T entidadeSalva = entityManager.merge(entidade);

		commitAndClose();
		return entidadeSalva;
	}

	public void deletarPorId(T entidade) {
		init();

		Object id = JPAUtil.getPrimaryKey(entidade);
		entityManager.createQuery("DELETE FROM " + entidade.getClass().getCanonicalName() + " WHERE id = " + id)
				.executeUpdate();

		commitAndClose();
	}
	
	public Tarefa buscarPorId(Long id) {
		init();

		Object t = entityManager.createNativeQuery("SELECT * FROM tarefa WHERE id = " + id, Tarefa.class).getSingleResult();

		commitAndClose();
		return (Tarefa) t;
	}

	public List<T> listar(Class<T> entidade) {
		init();

		List<T> entidadesList = entityManager.createQuery("FROM " + entidade.getName() + " ORDER BY id ASC", entidade)
				.getResultList();

		commitAndClose();
		return entidadesList;
	}

	public List<T> queryPersonalizada(String query, Class<T> entidade) {
		init();

		@SuppressWarnings("unchecked")
		List<T> entidadesList = entityManager.createNativeQuery(query, entidade).getResultList();

		commitAndClose();

		return entidadesList;
	}

}
