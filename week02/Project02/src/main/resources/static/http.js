;(function () {
  const BASE_URL = 'http://localhost:8080';
  const LOGIN_PAGE = 'login.html';

  if (typeof axios === 'undefined') {
    throw new Error('axios is not loaded');
  }

  const http = axios.create({
    baseURL: BASE_URL,
    timeout: 10000
  });

  http.interceptors.request.use(
    function (config) {
      const token = localStorage.getItem('token');
      if (token) {
        config.headers = config.headers || {};
        config.headers.Authorization = token;
      }
      return config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );

  http.interceptors.response.use(
    function (response) {
      return response;
    },
    function (error) {
      const status = error && error.response ? error.response.status : 0;
      if (status === 401) {
        localStorage.removeItem('token');
        alert('登录已过期，请重新登录');
        if (!location.href.endsWith('/' + LOGIN_PAGE) && !location.href.endsWith(LOGIN_PAGE)) {
          window.location.href = LOGIN_PAGE;
        }
      } else if (!error.response) {
        alert('网络异常，请检查后端服务是否启动');
      }
      return Promise.reject(error);
    }
  );

  window.httpClient = {
    request: function (url, method, data, config) {
      return http.request(Object.assign({
        url: url,
        method: method,
        data: data
      }, config || {})).then(function (resp) { return resp.data; });
    },
    upload: function (url, formData, config) {
      return http.request(Object.assign({
        url: url,
        method: 'POST',
        data: formData,
        headers: { 'Content-Type': 'multipart/form-data' }
      }, config || {})).then(function (resp) { return resp.data; });
    }
  };
})();

