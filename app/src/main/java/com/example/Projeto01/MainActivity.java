package com.example.Projeto01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.Projeto01.modelo.Produto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int requestCodeNovoProduto = 1;
    private final int resultCodeNovoProduto = 10;
    private final int requestCodeEditarProduto = 2;
    private final int resultCodeProdutoEditado = 11;
    private final int resultCodeProdutoExcluido = 12;

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");

        listViewProdutos = findViewById(R.id.listView_produtos);
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this, android.R.layout.simple_list_item_1, produtos);
        listViewProdutos.setAdapter(adapterProdutos);

        onClickListaProdutos();
    }

    private void onClickListaProdutos() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoSelecionado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProduto.class);
                intent.putExtra("produtoSelecionado", produtoSelecionado);
                startActivityForResult(intent, requestCodeEditarProduto);
            }
        });
    }

    public void onClickNovoProduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProduto.class);
        startActivityForResult(intent, requestCodeNovoProduto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == requestCodeNovoProduto && resultCode == resultCodeNovoProduto) {
            Produto produto = (Produto) data.getExtras().getSerializable("novoProduto");
            produto.setId(++id);
            this.adapterProdutos.add(produto);
        } else if (requestCode == requestCodeEditarProduto && resultCode == resultCodeProdutoEditado) {
            Produto produtoEditado = (Produto) data.getExtras().getSerializable("produtoEditado");
            for (int i = 0; i < adapterProdutos.getCount(); i++) {
                Produto produto = adapterProdutos.getItem(i);
                if (produto.getId() == produtoEditado.getId()) {
                    adapterProdutos.remove(produto);
                    adapterProdutos.insert(produtoEditado, i);
                    break;
                }
            }
        } else if (requestCode == requestCodeEditarProduto && resultCode == resultCodeProdutoExcluido) {
            Produto produtoExcluido = (Produto) data.getExtras().getSerializable("produtoExcluido");
            for (int i = 0; i < adapterProdutos.getCount(); i++) {
                Produto produto = adapterProdutos.getItem(i);
                if (produto.getId() == produtoExcluido.getId()) {
                    adapterProdutos.remove(produto);
                    break;
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}