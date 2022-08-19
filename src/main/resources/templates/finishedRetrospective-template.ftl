<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title> Recebemos seu pedido</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        html {
            font-size: 62.5%;
        }

        img {
            height: auto;
        }
        table {
            width: 300px;
        }

        .border {
            border: 1px solid #12101a;
            padding: 5px;
        }

        .margin {
            margin-bottom: 20px;
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
            <a href="https://retrocards-front.vercel.app/" target="_blank">
                <img  style="width:100%; display: block; margin: 20px auto;"
                      src="https://github.com/dayvidsonveiga/retrocards-back/blob/develop/src/main/resources/templates/banner.png?raw=true" alt="">
            </a>
        </td>
        <td style="width: 10px;"></td>
    </tr>
    <tr>
        <td style="width: 10px;"></td>
        <td>
            <div style="padding: 20px;">

                <h1 style="color: #5454fb; margin-bottom: 5px;">Olá </h1>
                <h5 style="font-size: 12px; margin-bottom: 10px"> A Retrospectiva de id: ${id} com titulo: ${title} foi concluída!</h5>
                <p style="margin-bottom: 20px;">Veja os itens apontados pelo time:</p>
                <table>
                    <tr>
                        <td class="border"><p style="text-align: center;">O que funcionou bem?</p></td>
                    </tr>
                    <tr>
                        <#list itemsWorked as item>
                        <td class="border margin">
                            <p>${item.idItemRetrospective}. ${item.title}</p>
                            <p>${item.description}</p>
                        </td>
                        </#list>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td class="border"><p style="text-align: center;">O que pode ser melhorado?</p></td>
                    </tr>
                    <tr>
                        <#list itemsImprove as item>
                        <td class="border margin">
                            <p>${item.idItemRetrospective}. ${item.title}</p>
                            <p>${item.description}</p>
                        </td>
                        </#list>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td class="border">
                            <p style="text-align: center;">O que faremos no próximo sprint para melhorar?</p>
                        </td>
                    </tr>
                    <tr>
                        <#list itemsNext as item>
                        <td class="border margin">
                            <p>${item.idItemRetrospective}. ${item.title} </p>
                            <p>${item.description}</p>
                        </td>
                        </#list>
                    </tr>
                </table>
            </div>
        </td>
        <td style="width: 10px;"></td>
    </tr>
    <tr>
    <tr>

    <tr>
        <td colspan="3">
                <span style="background-color: #5454fb; display: block; padding: 10px 0; margin: 0 auto;  width: 300px; text-align: center; color: #fff;">
                <a style="font-size: 15px; text-decoration: none; color: #fff;"
                   href='https://retrocards-front.vercel.app/retrospectiva/${id}' target="_blank">Clique aqui para abri-la </a></span>
        </td>
    </tr>


</table>

</body>
</html>