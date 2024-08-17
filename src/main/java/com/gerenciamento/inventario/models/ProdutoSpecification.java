package com.gerenciamento.inventario.models;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification {

    public static Specification<Produto> buscarProdutos(String name, Integer qtd, String categoriaId, String descricao, Boolean ativo) {
        return new Specification<Produto>() {
            @Override
            public Predicate toPredicate(Root<Produto> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();

                if (name != null && !name.isEmpty()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }

                if (qtd != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("qtd"), qtd));
                }

                if (categoriaId != null && !categoriaId.isEmpty()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("categoriaId"), categoriaId));
                }

                if (descricao != null && !descricao.isEmpty()) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("descricao"), "%" + descricao + "%"));
                }

                if (ativo != null) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ativo"), ativo));
                }

                return predicate;
            }
        };
    }
}
