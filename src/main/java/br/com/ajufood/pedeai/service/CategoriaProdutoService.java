package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.rest.dto.request.CategoriaProdutoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.CategoriaProdutoResponseDTO;
import br.com.ajufood.pedeai.exception.ConstraintException;
import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.CategoriaProdutoModel;
import br.com.ajufood.pedeai.repositoty.CategoriaProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaProdutoService {

  @Autowired
  private CategoriaProdutoRepository categoriaProdutoRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public CategoriaProdutoResponseDTO obterPorId(int id) {
    CategoriaProdutoModel categoria = categoriaProdutoRepository.findById(id)
      .orElseThrow(() -> new ObjectNotFoundException(
        "Categoria com ID " + id + " não encontrado"
      ));

    return modelMapper.map(categoria, CategoriaProdutoResponseDTO.class);
  }

  @Transactional(readOnly = true)
  public List<CategoriaProdutoResponseDTO> obterTodos() {
    return categoriaProdutoRepository.findAll()
      .stream()
      .map(categoriaProduto -> modelMapper.map(categoriaProduto, CategoriaProdutoResponseDTO.class))
      .toList();
  }

  @Transactional
  public CategoriaProdutoResponseDTO salvar(CategoriaProdutoRequestDTO dto) {

    try{
      CategoriaProdutoModel categoria = modelMapper.map(dto, CategoriaProdutoModel.class);
      validarCategoriaNomeExistente(categoria);
      CategoriaProdutoModel categoriaNova = categoriaProdutoRepository.save(categoria);
      return modelMapper.map(categoriaNova, CategoriaProdutoResponseDTO.class);

    }catch (DataIntegrityViolationException e){
      throw new DataIntegrityException("Erro de integridade ao ao salvar categoria " + dto.getNome() + ".", e);
    }
  }

  @Transactional
  public CategoriaProdutoResponseDTO atualizar(int id, CategoriaProdutoRequestDTO categoriaDto) {
    try {
      CategoriaProdutoModel categoriaAtualizadaModel = modelMapper.map(categoriaDto, CategoriaProdutoModel.class);
      CategoriaProdutoModel categoriaExistenteModel = categoriaProdutoRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException(
          "Categoria com ID " + id + " não encontrado"
        ));

      validarCategoriaNomeExistente(categoriaAtualizadaModel);

      categoriaExistenteModel.setNome(categoriaAtualizadaModel.getNome());
      categoriaExistenteModel.setDescricao(categoriaAtualizadaModel.getDescricao());

      CategoriaProdutoModel categoriaSalva = categoriaProdutoRepository.save(categoriaExistenteModel);

      return modelMapper.map(categoriaSalva, CategoriaProdutoResponseDTO.class);
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityException(
        "Erro de integridade ao atualizar a categoria " + categoriaDto.getNome() + ".", e
      );
    }

  }

  @Transactional
  public void deletar(int id){
    try{
      obterPorId(id);
      categoriaProdutoRepository.deleteById(id);
    }
    catch (DataIntegrityViolationException e){
      throw new DataIntegrityException("Erro de integridade ao excluir a categoria.", e);
    }
  }

  private void validarCategoriaNomeExistente(CategoriaProdutoModel categoria) {
    if(categoriaProdutoRepository.existsByNome(categoria.getNome())) {
        throw new ConstraintException("Já exite uma categoria com este nome: " + categoria.getNome() + ".");
    }
  }
}
