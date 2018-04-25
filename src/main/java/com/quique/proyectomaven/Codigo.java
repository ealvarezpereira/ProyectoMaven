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

/**
 * 
 * @param github Es un objeto de tipo github
 * @param git Es un objeto de tipo git
 */
public class Codigo {

    static GitHub github;
    static Git git;
    
    /**
     * 
     * @param usu Variable que recoge el usuario de la clase InicioSesion
     * @param ctra Variable que recoge la contraseña de la clase InicioSesion
     * @exception Error al conectarse al crear el repositorio
     */

    public static void crearRepositorio(String usu, String ctra) {

        try {
            
            //Nos conectamos a github utilizando el usuario y la contraseña pasandoselos por parametos
            github = GitHub.connectUsingPassword(usu, ctra);
            //Pedimos el nombre del repositorio
            String repoNombre = JOptionPane.showInputDialog("Introduzca el nombre del repositorio");
            
            //Creamos un objeto GHCreateRepositoryBuilder y le asignamos la creación del repositorio
            GHCreateRepositoryBuilder repo = github.createRepository(repoNombre);
            repo.create();
        } catch (IOException ex) {
            System.out.println("Error repositorio. " + ex);
        }
    }
    
    /**
     * 
     * @param urlclone Url del repositorio remoto del que hacemos el clonado
     * @param urlpath Url de la carpeta a la que hacemos el clonado
     * @exception Error al clonar el repositorio
     */

    public static void hacerClonado(String urlclone, String urlpath) {

        try {
            //Llamamos al metodo cloneRepository(), le pasamos la URI del repositorio remoto y la ruta de la carpeta
            Git.cloneRepository()
                    .setURI(Clonado.urlclone)
                    .setDirectory(new File(Clonado.urlpath))
                    .call();
        } catch (GitAPIException ex) {

            System.out.println("Error al clonar repositorio. " + ex);
        }

    }
    
    /**
     * 
     * @param urlcarpeta Es la ruta de la carpeta que seleccionamos que inicialice
     * @exception Error a la hora de inicializar el repositorio
     */

    public static void inicializarRepositorio(String urlcarpeta) {

        //Creamos un objeto de tipo InitCommand
        InitCommand comm = new InitCommand();

        try {
            //Al objeto le asignamos el directorio que queremos inicializar
            comm.setDirectory(new File(urlcarpeta)).call();
        } catch (GitAPIException ex) {
            System.out.println("Error al inicializar repositorio. " + ex);
        }
    }
    
    /**
     * @param mCommit Mensaje del commit
     * @param urlcarpeta Direccion del directorio en el que queremos hacer commit.
     */

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
            //Comando para hacer commit
            CommitCommand commit = git.commit();
            commit.setMessage(mCommit).call();
        } catch (IOException ex) {
            System.out.println("Error:" + ex);
        } catch (GitAPIException ex) {
            System.out.println("Error:" + ex);
        }
    }
    
    /**
     * 
     * @param httpUrl Url del repositorio al que hacemos push
     * @param localPath Direccion del repositorio
     */
    
    public static void hacerPush(String httpUrl, String localPath) {

        Repository localRepo;
        try {
            localRepo = new FileRepository(localPath+"/.git");
            Git git = new Git(localRepo);

            // Añadir repositorio remoto
            RemoteAddCommand remoteAddCommand = git.remoteAdd();
            remoteAddCommand.setName("origin");
             remoteAddCommand.setUri(new URIish(httpUrl));
            remoteAddCommand.call();

            // push to remote:
            PushCommand pushCommand = git.push();
            
            //Pedimos usuario y contraseña y los utiliza para conectarse
            
            String usu = JOptionPane.showInputDialog("Usuario");
            String ctra = JOptionPane.showInputDialog("Contraseña");
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(usu, ctra));
            pushCommand.call();
           
        } catch (URISyntaxException ex) {
            System.out.println("Error Uris sintax" + ex);
        } catch (GitAPIException ex) {
            System.out.println("Error gitApi " + ex);
        } catch (IOException ex) {
            System.out.println("Error IO " + ex);
        }

    }
}