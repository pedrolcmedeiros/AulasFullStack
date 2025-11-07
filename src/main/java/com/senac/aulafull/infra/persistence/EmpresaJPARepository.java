package com.senac.aulafull.infra.persistence;
import com.senac.aulafull.domain.entities.Empresa;
import com.senac.aulafull.domain.repository.EmpresaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EmpresaJPARepository
        extends JpaRepository<Empresa, UUID>, EmpresaRepository {

}