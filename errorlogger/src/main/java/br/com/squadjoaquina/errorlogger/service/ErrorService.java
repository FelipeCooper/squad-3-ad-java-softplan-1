package br.com.squadjoaquina.errorlogger.service;

import br.com.squadjoaquina.errorlogger.dto.ErrorDTO;
import br.com.squadjoaquina.errorlogger.mapper.ErrorMapper;
import br.com.squadjoaquina.errorlogger.model.Error;
import br.com.squadjoaquina.errorlogger.repository.ErrorRepository;
import br.com.squadjoaquina.errorlogger.service.exception.ErrorAlreadyArchivedException;
import br.com.squadjoaquina.errorlogger.service.exception.ErrorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;

    @Autowired
    public ErrorService(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    public ErrorDTO getById(Long id) {
        Optional<Error> opt = errorRepository.findById(id);
        if (opt.isPresent()) {
            return ErrorMapper.toDTO(opt.get());
        } else {
            throw new ErrorNotFoundException();
        }
    }

    public void save(ErrorDTO errorDTO){
        errorDTO.setCreatedAt(new Timestamp(System.currentTimeMillis()));//Ativar a auditoria para não ter que setar o timestamp na mão
        errorRepository.save(ErrorMapper.toError(errorDTO));
    }

    public void delete(Long id) {
        try {
            errorRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ErrorNotFoundException();
        }
    }

    public void stach(Long id) {
        ErrorDTO error = getById(id);
        if (error.isArchived()) {
            throw new ErrorAlreadyArchivedException();
        } else {
            error.setArchived(true);
            error.setArchivedAt(new Timestamp(System.currentTimeMillis()));
            errorRepository.save(ErrorMapper.toError(error));
        }
    }
}
