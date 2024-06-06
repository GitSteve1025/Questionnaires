# front_end

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

登录功能：
1.用户登录成功之后，才能访问index路径下的页面。
2.用户如果没有登录，那么会自动跳转到登录界面。
3.如果用户请求的是一个压根不存在的页面，依然强制回到登录首页。



解决方案：
1.无论是否已经登录，直接向后端请求用户信息
2.请求成功，登录成功
3.请求失败，登录未成功，跳转到/login

所以，目的是写向后端请求用户信息的代码

