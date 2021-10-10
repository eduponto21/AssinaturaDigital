/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.utfpr.sistemasdistribuidos.assinaturadigital;

/**
 *
 * @author edupo
 */
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

//O destinatário é quem vai receber a mensagem, a assinatura digital e a chave pública para fazer a validação da mensagem recebida.
public class DestinatarioAssiDig {

    public void recebeMensagem(PublicKey pubKey, String mensagem, byte[] assinatura) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        Signature clientSig = Signature.getInstance(AssinaturaDigital.algoritmo);
        clientSig.initVerify(pubKey);
        clientSig.update(mensagem.getBytes());

        if (clientSig.verify(assinatura)) {
            //Mensagem corretamente assinada
            System.out.println("A Mensagem recebida foi assinada corretamente.");
        } else {
            //Mensagem não pode ser validada
            System.out.println("A Mensagem recebida NÃO pode ser validada.");
        }
    }

}
