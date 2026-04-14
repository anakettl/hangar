package com.example.hangar.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Classe base para entidades JPA com auditoria e controle de versao.
 *
 * <p>Fornece identificador, campos de criacao/atualizacao e callbacks de ciclo de vida para
 * preenchimento automatico das datas. Subclasses devem implementar {@link #getUniqueField()} para
 * compor a comparacao de igualdade quando a entidade ainda nao foi persistida.</p>
 */
@MappedSuperclass
public abstract class AuditableEntity {
    /**
     * Identificador unico gerado pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * Versao otimista usada para controle de concorrencia.
     */
    @Version
    protected Long version;

    /**
     * Data/hora de criacao do registro (imutavel apos persistencia).
     */
    @Column(nullable = false, updatable = false)
    protected LocalDateTime creationDate;

    /**
     * Data/hora da ultima modificacao do registro.
     */
    @Column(nullable = false)
    protected LocalDateTime lastModifiedDate;

    /**
     * Usuario responsavel pela criacao do registro.
     */
    @Column(length = 100)
    protected String createdBy;

    /**
     * Usuario responsavel pela ultima alteracao do registro.
     */
    @Column(length = 100)
    protected String lastModifiedBy;

    /**
     * Callback executado antes da primeira persistencia.
     *
     * <p>Inicializa os campos de auditoria temporal com o mesmo valor de data/hora.</p>
     */
    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
        lastModifiedDate = creationDate;
    }

    /**
     * Callback executado antes de cada atualizacao.
     *
     * <p>Atualiza apenas a data/hora de ultima modificacao.</p>
     */
    @PreUpdate
    protected void onUpdate() {
        lastModifiedDate = LocalDateTime.now();
    }

    /**
     * Compara entidades pelo identificador quando ambas ja estao persistidas.
     *
     * <p>Para entidades transient (sem {@code id}), usa {@code creationDate} e o valor retornado por
     * {@link #getUniqueField()}.</p>
     *
     * @param obj objeto de comparacao
     * @return {@code true} quando representam a mesma entidade segundo as regras acima
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AuditableEntity that = (AuditableEntity) obj;

        // Para persistidas: compara ID
        if (id != null && that.id != null) {
            return id.equals(that.id);
        }

        // Para transient: creationDate + uniqueField
        return Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(getUniqueField(), that.getUniqueField());
    }

    /**
     * Calcula hash com base nas mesmas regras usadas em {@link #equals(Object)}.
     *
     * <p>Quando houver {@code id}, ele participa do hash; caso contrario, o hash usa
     * {@code creationDate} e {@link #getUniqueField()}.</p>
     *
     * @return valor de hash da entidade
     */
    @Override
    public int hashCode() {
        // Mesmo padrao: ID se disponivel, senao creationDate + uniqueField
        if (id != null) {
            return Objects.hash(creationDate, id);  // creationDate + ID
        }

        return Objects.hash(creationDate, getUniqueField());  // creationDate + uniqueField
    }

    /**
     * Retorna um campo unico/estavel da subclasse para comparacao de entidades transient.
     *
     * <p>Exemplos comuns: username, email, codigo de negocio.</p>
     *
     * @return valor unico utilizado na comparacao de igualdade sem {@code id}
     */
    protected abstract Object getUniqueField();
}
