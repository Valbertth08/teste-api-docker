package com.example.docker.mapper;

import com.example.docker.domain.Aluno;
import com.example.docker.dto.AlunoCadastrarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlunoMapper {

    Aluno alunoCadastrarDtoToAluno(AlunoCadastrarDTO dados);
}
