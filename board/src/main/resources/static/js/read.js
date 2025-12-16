const url = `http://localhost:8080/replies/board/${bno}`;

fetch(url)
  .then((res) => {
    if (!res.ok) {
      throw new Error(`에러 발생 ${res.status}`);
    }
    return res.json();
  })
  .then((data) => {
    console.log(data);
  });
