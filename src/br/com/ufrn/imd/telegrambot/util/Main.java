package br.com.ufrn.imd.telegrambot.util;

import br.com.ufrn.imd.telegrambot.controladores.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.*;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //TOKEN - 828843603:AAFH1uGcYykndT--HkDv9iJv16x11TNUDIc
        //Nome: Patrimonio bot username:patrimoniolp2_bot

        //inicialização do Bot
        TelegramBot bot = new TelegramBot("828843603:AAFH1uGcYykndT--HkDv9iJv16x11TNUDIc");
        //objeto responsável por receber as mensagens
        GetUpdatesResponse updatesResponse;
        //objeto responsável por gerenciar o envio de respostas
        SendResponse sendResponse = null;
        //objeto responsável por gerenciar o envio de ações do chat
        BaseResponse baseResponse;
        //controle de off-set, isto é, a partir deste ID será lido as mensagens pendentes na fila
        int m = 0;

        //Controladores
        List<String> mensagens = new ArrayList<String>();
        Controlador operacaoAtual = null;
        List<Controlador> operacoes = new ArrayList<Controlador>();
        //Inicializando todos os controladores
        operacoes.add(new ControladorCadastroLocalizacao());
        operacoes.add(new ControladorCadastroCategoria());
        operacoes.add(new ControladorCadastroBem());
        operacoes.add(new ControladorListarLocalizacao());
        operacoes.add(new ControladorListarCategoria());
        operacoes.add(new ControladorListarBem());
        operacoes.add(new ControladorBuscarBemCodigo());
        operacoes.add(new ControladorBuscarBemDescricao());
        operacoes.add(new ControladorBuscarBemNome());
        operacoes.add(new ControladorGerarRelatorioChat());
        operacoes.add(new ControladorGerarRelatorioArquivo());
        operacoes.add(new ControladorApagarBem());
        operacoes.add(new ControladorMovimentarBem());
        operacoes.add(new ControladorApagarLocalizacao());
        operacoes.add(new ControladorApagarCategoria());

        //loop infinito pode ser alterado por algum timer de intervalo curto
        while (true){
            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
            updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));
            //lista de mensagens
            List<Update> updates = updatesResponse.updates();
            //análise de cada ação da mensagem
            for (Update update : updates) {
                //atualização do off-set
                m = update.updateId()+1;
                System.out.println("Recebendo mensagem:"+ update.message().text());
                //envio de "Escrevendo" antes de enviar a resposta
                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
                //verificação de ação de chat foi enviada com sucesso
                System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());

                //Tratamento de mensagens do chat

                if(operacaoAtual == null){ //Enquanto não estiver dentro de uma operação o bot procura a mensagem recebida no chat na lista de comandos
                    for(Controlador controlador: operacoes){
                        if(controlador.getOperacao().equals(update.message().text())){
                            operacaoAtual = controlador; //Recebe a operação ao receber o nome da operação em tela
                            break;
                        }
                    }
                    if(operacaoAtual != null){
                        mensagens = operacaoAtual.chat(update.message().text());
                    }
                    else{
                        if(update.message().text().equals("/ajuda")) { //Comando para mostrar as operações disponiveis no chat
                            mensagens.add("Os comandos disponíveis são:\n /addlocalizacao - Cadastrar localização.\n /addcategoria - Cadastrar categoria." +
                                    "\n /addbem - Cadastrar bem.\n /listarlocalizacao - Listar localizações cadastradas. " +
                                    "\n /listarcategoria - Listar categorias cadastradas.\n /listarbem - Listar bens cadastrados em uma localização."+
                                    "\n /buscarbemcodigo - Procurar por determinado bem a partir de seu código" +
                                    "\n /buscarbemdescricao - Procurar por determinado bem a partir de sua descrição" +
                                    "\n /buscarbemnome - Procurar por determinado bem a partir de seu nome" +
                                    "\n /relatoriochat - Gerar relatório nesse chat \n /relatorioarquivo - Gerar relatorio em arquivo" +
                                    "\n /apagarbem - remover bem do cadastro \n /movimentarbem - Modifica a localização de um bem" +
                                    "\n /apagarlocalizacao - remover localização do cadastro\n /apagarcategoria - remover categoria do cadastro");
                        }
                        else { //Caso a mensagem não seja um dos comandos possiveis, mostrar essa mensagem no chat
                            mensagens.add("Não consigo realizar essa operação.\n\nPara conferir as operações disponíveis, utilize o comando /ajuda.");
                        }
                    }
                }
                else{
                    if(update.message().text().equals("/cancelar")){ //Comando para sair de dentro de uma operação
                        mensagens.add("A operação foi cancelada");
                        operacaoAtual.reset(); //O controlador é resetado para evitar conflito em reuso
                        operacaoAtual = null; //Removendo a operação da variavel para  permitir inicio de outra operação
                    }
                    else{
                        if(update.message().text().charAt(0) == '/'){ //Mensagem quando o usuario tentar iniciar uma operação quando já estiver dentro de uma outra operação
                            mensagens.add("Para escolher outra operação é necessario que a operação atual seja cancelada através do comando /cancelar");
                        }
                        else{
                            mensagens = operacaoAtual.chat(update.message().text()); //Local onde ocorre a troca de mensagens com o controlador
                            if(operacaoAtual.getPasso() == operacaoAtual.getPassosTotal() + 1){
                                operacaoAtual.reset(); //O controlador é resetado para evitar conflito em reuso
                                operacaoAtual = null; //Removendo a operação da variavel para  permitir inicio de outra operação
                            }
                        }
                    }
                }

                for(String mensagem: mensagens){
                    //envio da mensagem de resposta
                    sendResponse = bot.execute(new SendMessage(update.message().chat().id(), mensagem));
                }
                //verificação de mensagem enviada com sucesso
                System.out.println("Mensagem Enviada?" + sendResponse.isOk());
                mensagens.clear();
            }
        }
    }
}
