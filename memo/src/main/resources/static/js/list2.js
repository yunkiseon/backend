const url = "http://localhost:8080/memo";

// fetch는 window 함수이기에 기본적으로 사용할 수 있다.
// get 방식일 때
fetch(url)
  .then((res) => {
    if (!res.ok) {
      throw new Error(`error! ${res.status}`);
    }
    return res.json();
  })
  .then((data) => {
    console.log(data);
    let result = "";
    data.forEach((memo) => {
      result += `<tr>`;
      result += `<th scope="row">${memo.id}</th>`;
      result += `<td>`;
      result += `<a href="/memo/read2?id=${memo.id}">${memo.text}</a>`;
      result += `<td>${memo.createDate}</td>`;
      result += `<td>${memo.updateDate}</td>`;
      result += `</tr>`;
    });
    document.querySelector("table tbody").insertAdjacentHTML("afterbegin", result);
  })
  .catch((err) => console.log(err));
// data만 주고받는 형태에서는 이렇게 하는 것보다 react 형식이 더 편하다.
