package br.com.jonjts.myprofit.callback

public interface DatabaseCallback{

    // AO inserir, remover ou alterar um item esse metodo será disparado
    fun onDataChange()
}