/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quique.proyectomaven;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

/**
 *
 * @author quique
 */
public class Codigo {

    static GitHub github;

    public static void crearRepositorio(String usu, String ctra) {

        try {
            github = GitHub.connectUsingPassword(usu, ctra);
            String repoNombre = JOptionPane.showInputDialog("Introduzca el nombre del repositorio");
            GHCreateRepositoryBuilder repo = github.createRepository(repoNombre);
            repo.create();
        } catch (IOException ex) {
            System.out.println("Error repositorio. " + ex);
        }
    }

    public static void hacerClonado(String urlclone, String urlpath) {

        try {
            Git.cloneRepository()
                    .setURI(ElegirPath.urlclone)
                    .setDirectory(new File(ElegirPath.urlpath))
                    .call();
        } catch (GitAPIException ex) {

            System.out.println("Error al clonar repositorio. " + ex);
        }

    }

    public static void inicializarRepositorio(String url) {

        InitCommand comm = new InitCommand();

        try {
            comm.setDirectory(new File(url)).call();
        } catch (GitAPIException ex) {
            System.out.println("Error al inicializar repositorio. " + ex);
        }
    }

}
