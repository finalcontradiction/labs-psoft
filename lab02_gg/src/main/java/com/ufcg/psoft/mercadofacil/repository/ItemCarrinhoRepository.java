package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;
import com.ufcg.psoft.mercadofacil.model.ids.ItemCarrinhoId;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, ItemCarrinhoId> {

	@Query("SELECT item FROM ItemCarrinho item WHERE item.id.carrinho.id =:carrinhoId AND item.id.produto.id =:prodId")
	public Optional<ItemCarrinho> findById(long carrinhoId, long prodId);
	
	public List<ItemCarrinho> deleteByIdCarrinhoId(long carrinhoId);
	
	public List<ItemCarrinho> deleteByIdCarrinhoIdAndIdProdutoId(long carrinhoId, long produtoId);
	
	default List<ItemCarrinho> deleteById(long carrinhoId, long produtoId) {
		return deleteByIdCarrinhoIdAndIdProdutoId(carrinhoId, produtoId);
	}
}
