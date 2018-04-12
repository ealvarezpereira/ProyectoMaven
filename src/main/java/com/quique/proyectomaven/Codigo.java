/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quique.proyectomaven;

import java.io.*;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.*;
import org.kohsuke.github.*;

/**
 *
 * @author quique
 */
public class Codigo {

    static GitHub github;
    static Git git;

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
                    .setURI(Clonado.urlclone)
                    .setDirectory(new File(Clonado.urlpath))
                    .call();
        } catch (GitAPIException ex) {

            System.out.println("Error al clonar repositorio. " + ex);
        }

    }

    public static void inicializarRepositorio(String urlcarpeta) {

        InitCommand comm = new InitCommand();

        try {
            comm.setDirectory(new File(urlcarpeta)).call();
        } catch (GitAPIException ex) {
            System.out.println("Error al inicializar repositorio. " + ex);
        }
    }

    public static void hacerCommit(String mCommit, String urlcarpeta) {
        try {
            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
            Repository repository = repositoryBuilder.setGitDir(new File(urlcarpeta + "/.git"))
                    .readEnvironment()
                    .findGitDir()
                    .setMustExist(true)
                    .build();

            Git git = new Git(repository);
            AddCommand add = git.add();
            add.addFilepattern(urlcarpeta + "/.git").call();
            CommitCommand commit = git.commit();
            commit.setMessage(mCommit).call();
        } catch (IOException ex) {
            System.out.println("Error:" + ex);
        } catch (GitAPIException ex) {
            System.out.println("Error:" + ex);
        }
    }
}