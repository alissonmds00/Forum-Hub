package dev.alisson_matias.Forum.Hub.domain.resposta;

import org.springframework.stereotype.Service;

@Service
public class ArquivarResposta {
    private RespostaRepository respostaRepository;

    public void arquivar(Long id, String emailAutor) {
        var resposta = respostaRepository.findByIdAndAtivoTrue(id);
        if (resposta == null) {
            throw new RuntimeException("Resposta não encontrada.");
        }
        var autorResposta = resposta.getAutor().getEmail();
        if (!autorResposta.equals(emailAutor)) {
            throw new RuntimeException("Você não tem permissão para isso");
        }
        resposta.excluirResposta();
    }
}
