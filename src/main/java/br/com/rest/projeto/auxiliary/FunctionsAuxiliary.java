package br.com.rest.projeto.auxiliary;

import br.com.rest.projeto.entity.NCServico;
import br.com.rest.projeto.entity.entityEnum.StatusServico;
import br.com.rest.projeto.DTO.responseDTO.NCServicoResponseDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class FunctionsAuxiliary {

    public static String findExtensionFromBase64(String mime){
        return mime.substring(mime.indexOf("/")+1, mime.indexOf(";base64"));
    }

    public static String formatMimeBase64(String mime) {
        return mime.substring(mime.indexOf("base64,")+7);
    }

    public static String removedPathOfFotosName(String name) {
        return name.substring(name.indexOf("fotos/")+6);
    }

    public static String retornaMensagem(NCServicoResponseDTO ncServicoResponseDTO, String messageForWho) throws ParseException {
        String mensagem;
        if (messageForWho.equals("RESPONSAVEL")){
            mensagem = "Olá, você foi indicado como responsável pela resolução " + defineServiceOrNC(ncServicoResponseDTO.getTipoCadastro(), false) + ": " + ncServicoResponseDTO.getId() + ". Aqui estão algumas informações:\n";
            mensagem += "*Obra*: " + ncServicoResponseDTO.getProjeto().getDescricao() + "\n";
            if (ncServicoResponseDTO.getTorre() != null) {
                mensagem += "*Torre*: " + ncServicoResponseDTO.getTorre() + "\n";
            }
            if (ncServicoResponseDTO.getPavimento() != null) {
                mensagem += "*Pavimento*: " + ncServicoResponseDTO.getPavimento().getDescricao() +"\n";
            }
            if (ncServicoResponseDTO.getUnidade() != null){
                mensagem += "*Unidade*: " + ncServicoResponseDTO.getUnidade().getDescricao() + "\n";
            }
            mensagem += "*Serviço*: " + ncServicoResponseDTO.getTipoServico().getDescricao() +"\n";
            mensagem += "*Data*: " + formatDataUSAToBR(ncServicoResponseDTO.getDataInicio()) + "\n";
            mensagem += "*Responsável*: " + ncServicoResponseDTO.getFuncionario().getNome() + "\n";
            mensagem += "*Prazo*: " + formatDataUSAToBR(ncServicoResponseDTO.getDataPrevisaoTermino()) + "\n";
            mensagem += "*Situação*: " + formatStatus(ncServicoResponseDTO.getStatus()) + "\n";
            mensagem += "*Descrição*: " + ncServicoResponseDTO.getDescricao() + "\n";
            if (ncServicoResponseDTO.getPrescricao() != null){
                mensagem += "*Prescrição*: " + ncServicoResponseDTO.getPrescricao() + "\n";
            }
            if (ncServicoResponseDTO.getFoto() != null){
                mensagem += "*Foto(s)* disponivel no link abaixo:\n";
                mensagem += writeFotos(ncServicoResponseDTO.getFoto());
            }
            mensagem += "\nPor enquanto este registro está com a situação " +formatStatus(ncServicoResponseDTO.getStatus()) + ". Após a resolução informe ao responsável a nova situação";
        } else {
            mensagem = "O cadastro " + defineServiceOrNC(ncServicoResponseDTO.getTipoCadastro(), false) + " para a *Obra* *" + ncServicoResponseDTO.getProjeto().getDescricao() + "* com a descrição:\n";
            mensagem += ncServicoResponseDTO.getDescricao() + "\n";
            mensagem += "Possui a ID:\n";
            mensagem += ">>> *" + ncServicoResponseDTO.getId()+ "* <<<\n\n";
            mensagem += "Para mais informações acesse o aplicativo";
        }
        return mensagem;
    }

    static String formatStatus(String status) {
        String statusFormatado = "";
        switch (status) {
            case "CONCLUIDO":
                statusFormatado = "Concluido";
                break;
            case "PENDENTE":
                statusFormatado = "Pendente";
                break;
            case "EM_EXECUCAO":
                statusFormatado = "Em execução";
                break;
            case "VENCIDO":
                statusFormatado = "Vencido";
                break;
            case "AGENDADO":
                statusFormatado = "Agendado";
                break;
        }
        return statusFormatado;
    }

    public static String setStatusCorretoPelaData(NCServico ncServico) {
        String status = "EM_EXECUCAO";
        LocalDate dataAtual = retornaDataAtualEmLocalDate(new Date());

        if (ncServico.getStatus() == StatusServico.CONCLUIDO) {
            status = "CONCLUIDO";
        } else if (ncServico.getDataPrevisaoTermino().isEqual(dataAtual)) {
            status = "PENDENTE";
        } else if (dataAtual.isAfter(ncServico.getDataPrevisaoTermino())) {
            status = "VENCIDO";
        } else if (dataAtual.isBefore(ncServico.getDataInicio())){
            status = "AGENDADO";
        }

        return status;
    }

    static String formatDataUSAToBR(String data) throws ParseException {
        SimpleDateFormat formatBR = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatUS = new SimpleDateFormat("yyyy-MM-dd");
        return formatBR.format(formatUS.parse(data));
    }

    private static String writeFotos(List<String> fotos) {
        StringBuilder fotosEscritas = new StringBuilder();
        for (String foto : fotos){
            fotosEscritas.append("").append(foto).append("\n");
        }
        return fotosEscritas.toString();
    }

    static String defineServiceOrNC(String tipoCadastro, Boolean justName) {
        String tipoCad = " ";
        if (!justName) {
            tipoCad = "da ";
            if (tipoCadastro.equals("SERVICO")){
                tipoCad = "do ";
            }
        }
        if (tipoCadastro.equals("SERVICO")){
            tipoCad += "Serviço";
        } else {
            tipoCad += "Não Conformidade";
        }
        return tipoCad.trim();
    }

    public static LocalDate retornaDataAtualEmLocalDate(Date date) {
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
}
