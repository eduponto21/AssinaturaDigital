/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utfpr.sistemasdistribuidos.assinaturadigital;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;

/**
 *
 * @author edupo
 */
/**
 * Hello world!
 *
 */
//Agora podemos testar os códigos através de uma classe principal que gera diferentes tipos de mensagens, assinaturas e chaves para validar a mensagem por um destinatário.
public class AssinaturaDigital {
    
    public static final String algoritmo = "DSA";

    public static void main(String args[]) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //Faz sentido criar uma nova chave privada/pública a cada mensagem?
        //Ou idealmente o remetente teria um array de chaves privadas e públicas e cada destinatário guardaria uma chave pública diferente deste remetente?
        //Faz sentido essa pergunta?
        
        //MD5withRSA é seguro? Oracle desabilitou o acesso a esse algorítmo, tem que editar os configs files diretamente do java (não está no Java Control Panel)
        //https://antoniocampos.net/2018/01/12/java-erro-ao-utilizar-md5withrsa/
        //https://matthieu.yiptong.ca/2018/04/08/enabling-md5withrsa-in-newer-versions-of-java/
        //Li a respeito que o DSA é patentiado pelos EUA, logo poderiam talvez "quebrar" a criptografia, RSA seria uma alternativa "global".
        
        //Remetente Gera Assinatura Digital para uma Mensagem
        RemetenteAssiDig remetenteAssiDig = new RemetenteAssiDig();
        String mensagem = "Exemplo de mensagem.";
        byte[] assinatura = remetenteAssiDig.geraAssinatura(mensagem);
        
        //Guarda Chave Pública para ser Enviada ao Destinatário
        PublicKey pubKey = remetenteAssiDig.getPubKey();

        //Destinatário recebe dados correto
        DestinatarioAssiDig destinatarioAssiDig = new DestinatarioAssiDig();
        destinatarioAssiDig.recebeMensagem(pubKey, mensagem, assinatura);

        //Destinatário recebe mensagem alterada
        String msgAlterada = "Exemplo de mensagem alterada.";
        destinatarioAssiDig.recebeMensagem(pubKey, msgAlterada, assinatura);

        //Criando outra Assinatura
        //Mesmo se utilizar a mesma mensagem (String: mensagem) a assinatura muda, portanto falha.
        String mensagem2 = "Exemplo de outra mensagem.";
        byte[] assinatura2 = remetenteAssiDig.geraAssinatura(mensagem2);
        
        //Guarda Chave Pública para ser Enviada ao Destinatário
        PublicKey pubKey2 = remetenteAssiDig.getPubKey();

        //Destinatário recebe outra assinatura
        destinatarioAssiDig.recebeMensagem(pubKey, mensagem, assinatura2);

        //Destinatário recebe outra chave pública
        destinatarioAssiDig.recebeMensagem(pubKey2, mensagem, assinatura);

    }

}
