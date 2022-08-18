<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Retrospectiva</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            border: 0 none;
        }

        html {
            font-size: 100%;
        }

        span, p {
            width: 100%;
            text-align: center;
            margin-bottom: 10px;
        }

        ul {
            width: 100%;
            text-align: center;
        }

        ul li {
            width: 100%;
            list-style: none;
            padding: 10px;
        }

        @media (max-width: 600px) {

            h1 {
                font-size: 1rem;
            }
        }
    </style>
</head>
<body style="background-color:#fff">


<a href="https://retrocards-front.vercel.app/" target="_blank">
    <img style="width:300px; display: block; margin: 20px auto;" src="https://github.com/dayvidsonveiga/retrocards-back/blob/develop/src/main/resources/templates/banner.png?raw=true" alt="">
</a>

<div style="padding: 20px; display: flex; flex-direction: column; align-items: center;">
    <span style="color: #5454fb;font-weight: 700;">Olá,</span>
    <span>A Retrospectiva de id: ${id} com titulo: ${title} foi concluída!</span>
    <span>Veja os itens apontados pelo time:</span>
    <p style="font-weight: 600;">O que funcionou bem?</p>
    <ul>
        <#list itemsWorked as item>
        <li>${item.idItemRetrospective} - ${item.title} - ${item.description}</li>
        </#list>
    </ul>
    <p style="font-weight: 600;">O que pode ser melhorado?</p>
    <ul>
        <#list itemsImprove as item>
        <li>${item.idItemRetrospective} - ${item.title} - ${item.description}</li>
        </#list>
    </ul>
    <p style="font-weight: 600;">O que faremos no próximo sprint para melhorar?</p>
    <ul>
        <#list itemsNext as item>
        <li>${item.idItemRetrospective} - ${item.title} - ${item.description}</li>
        </#list>
    </ul>
</div>
<div style="display: flex; justify-content:center">
    <a style="font-size: 15px; text-decoration: none; color: #fff; background-color: #12101a; display: flex; justify-content:center;align-items: center; width: 300px; height: 30px; border-radius: 5px"
       href="https://retrocards-front.vercel.app/retrospectiva/5" target="_blank">
        Clique aqui para abri-la
    </a>
</div>
</body>
</html>