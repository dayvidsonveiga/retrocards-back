<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Recebemos seu pedido</title>
    <style>
        * {
            margin: 0 0 10px;
            padding: 0;
            border: 0 none;
        }

        html {
            font-size: 62.5%;
        }

        img {
            max-width: 100%;
            height: auto;
        }

        li {
            list-style: none;
            display: flex;
            flex-direction: column;
        }

        @media (max-width: 600px) {
            table {
                width: 100% !important;
                min-width: 320px;
            }

            h1 {
                font-size: 1rem;
            }
        }
    </style>
</head>
<body >


<table  width: 600px; margin: 20px auto; font-family: Verdana, sans-serif;>
    <tr>
        <td style="width: 10px;"></td>
        <td>
            <img style="border-radius: 0%; width:100%; display: block; margin: 20px auto;"
                 src="https://github.com/dayvidsonveiga/retrocards-back/blob/develop/src/main/resources/templates/banner.png?raw=true" alt=""></a>
        </td>
        <td style="width: 10px;"></td>
    </tr>
    <tr>
        <td style="width: 10px;"></td>
        <td>
            <div style="padding: 20px;">

                <h1 style="color: #98519b">Olá </h1>
                <h5> A Retrospectiva de id: ${id} com titulo: ${title} foi concluída!</h5>
                <p>Veja os itens apontados pelo time:</p>
                <p>O que funcionou bem?</p>
                <ul>
                    <#list itemsWorked as item>
                    <li>
                        <#if item?length == 0>
                        <span>Nenhum item cadastrado que funcionou bem</span>
                        </#if>
                        <span>${item.idItemRetrospective}. ${item.title} | ${item.description}</span>
                    </li>
                    </#list>
                </ul>
                <p>O que pode ser melhorado?</p>
                <ul>
                    <#list itemsImprove as item>
                    <li>
                        <#if item?length == 0>
                        <span>Nenhum item cadastrado que precisa ser melhorado</span>
                        </#if>
                        <span>${item.idItemRetrospective}. ${item.title} | ${item.description}</span>
                    </li>
                    </#list>
                </ul>
                <p>O que faremos no próximo sprint para melhorar?</p>
                <ul>
                    <#list itemsNext as item>
                    <li>
                        <#if item?length == 0>
                        <span>Nenhum item cadastrado que será realizado na próxima sprint para melhorar</span>
                        </#if>
                        <span>${item.idItemRetrospective}. ${item.title} | ${item.description}</span>
                    </li>
                    </#list>
                </ul>
            </div>
        </td>
        <td style="width: 10px;"></td>
    </tr>
    <tr>
    <tr>

    <tr>
        <td colspan="3">
                <span style="background-color: #98519b; display: block; padding: 10px 5px 7px; margin: 0 auto;  width: 300px; height: 30px; text-align: center;  margin-bottom: 30px;  border-style: dashed;  color: #fff;">
                <a style="font-size: 15px; text-decoration: none; color: #fff;"
                   href="https://milled.com/brazil-empiricus-cpl/acabaram-as-newsletters-0mx4ioTVyg4xnFse">Clique aqui para abri-la </a></span>
        </td>
    </tr>


</table>

</body>
</html>