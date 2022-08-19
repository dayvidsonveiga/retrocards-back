<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Recebemos seu pedido</title>
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
            min-width: 60%;
            height: auto;
        }

        .border {
            border: 1px solid #12101a;
            padding: 5px;
        }

        .margin {
            margin-bottom: 20px;
        }

        .padding {
            padding: 10px;
        }

        li {
            list-style: none;
            display: flex;
            flex-direction: column;
        }

        .table {
            width: 400px;
        }
    </style>
</head>
<body>
<table style="padding: 20px">
    <tbody>
    <tr>
        <td style="width: 5%"></td>
        <td style="width: 90%">
            <a
                    href="https://retrocards-front.vercel.app/"
                    target="_blank"
            >
                <img
                        style="width: 40%"
                        src="https://github.com/dayvidsonveiga/retrocards-back/blob/develop/src/main/resources/templates/banner.png?raw=true"
                        alt=""
                />
            </a>
        </td>
        <td style="width: 5%"></td>
    </tr>
    <tr>
        <td style="width: 5%"></td>
        <td style="width: 90%">
            <div style="margin: 20px 0">
                <h1 style="color: #5454fb; margin-bottom: 5px">
                    Olá
                </h1>
                <h5 style="font-size: 12px; margin-bottom: 10px">
                    A Retrospectiva de id: ${id} com titulo:
                    ${title} foi concluída!
                </h5>
                <p style="margin-bottom: 20px">
                    Veja os itens apontados pelo time:
                </p>
                <table class="table">
                    <tr>
                        <td style="height: 30px" class="border">
                            <p style="text-align: center">
                                O que funcionou bem?
                            </p>
                        </td>
                    </tr>
                    <#list itemsWorked as item>
                    <tr>
                        <td class="border margin">
                            <p class="padding">
                                ${item.idItemRetrospective}.
                                ${item.title}
                            </p>
                            <p class="padding">
                                ${item.description}
                            </p>
                        </td>
                    </tr>
                    </#list>
                </table>
                <table class="table">
                    <tr>
                        <td style="height: 30px" class="border">
                            <p style="text-align: center">
                                O que pode ser melhorado?
                            </p>
                        </td>
                    </tr>
                    <#list itemsImprove as item>
                    <tr>
                        <td class="border margin">
                            <p class="padding">
                                ${item.idItemRetrospective}.
                                ${item.title}
                            </p>
                            <p class="padding">
                                ${item.description}
                            </p>
                        </td>
                    </tr>
                    </#list>
                </table>
                <table class="table">
                    <tr>
                        <td style="height: 30px" class="border">
                            <p style="text-align: center">
                                O que faremos no próximo sprint para
                                melhorar?
                            </p>
                        </td>
                    </tr>
                    <#list itemsNext as item>
                    <tr>
                        <td class="border margin">
                            <p class="padding">
                                ${item.idItemRetrospective}.
                                ${item.title}
                            </p>
                            <p class="padding">
                                ${item.description}
                            </p>
                        </td>
                    </tr>
                    </#list>
                </table>
            </div>
        </td>
        <td style="width: 5%"></td>
    </tr>
    <tr>
        <td style="width: 5%"></td>
        <td style="width: 90%">
            <div style="margin: 20px 0 0 0">
                <button
                        style="
                                    background-color: #5454fb;
                                    display: block;
                                    border: none;
                                    padding: 10px 0;
                                    width: 300px;
                                    text-align: center;
                                    color: #fff;
                                "
                >
                    <a
                            style="
                                        font-size: 15px;
                                        text-decoration: none;
                                        color: #fff;
                                    "
                            href="https://retrocards-front.vercel.app/retrospectiva/${id}"
                            target="_blank"
                    >Clique aqui para abri-la
                    </a>
                </button>
            </div>
        </td>
        <td style="width: 5%"></td>
    </tr>
    </tbody>
</table>
</body>
</html>