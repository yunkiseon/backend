const url = `http://localhost:8080/replies`;
const csrfVal = document.querySelector("#replyForm").querySelector("[name='_csrf']").value;

const replyList = document.querySelector(".replyList");

// 댓글 추가 -> replyForm submit 이 발생 시
// submit 기능 중지
// {
//     "text": "reply 추추추가",
//     "replyer": "guest4",
//     "bno": 103
// }
// 댓글 작성인가 수정인가를 구별하려면 rno value 값의 존재여부로 확인하면 된다.

document.querySelector("#replyForm").addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const rno = form.rno.value;
  const reply = {
    rno: rno,
    text: form.text.value,
    replyerEmail: form.replyerEmail.value,
    bno: bno,
  };

  if (!rno) {
    //new
    fetch(`${url}/new`, {
      method: "POST",
      headers: {
        "X-CSRF-TOKEN": csrfVal,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(reply),
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "댓글 작성 완료",
            icon: "success",
            draggable: true,
          });
        }
        // form.reset 도 가능하지만 rno 값을 남겨야하기 때문에.
        // form.replyer.value = "";
        form.text.value = "";
        loadReply();
      })
      .catch((err) => console.log(err));
  } else {
    //modify
    fetch(`${url}/${rno}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrfVal,
      },
      body: JSON.stringify(reply),
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "댓글 수정 완료",
            icon: "success",
            draggable: true,
          });
        }
        // form.reset 도 가능하지만 rno 값을 남겨야하기 때문에.
        // form.replyer.value = "";
        form.text.value = "";
        form.rno.value = "";
        form.rbtn.innerHTML = "댓글 작성";
        loadReply();
      })
      .catch((err) => console.log(err));
  }
});

// 날짜 시간
const formatDate = (data) => {
  const date = new Date(data);
  //2025/12/16 12:20 식으로 만들고 싶다.
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 댓글 목록 가져오기

const loadReply = () => {
  fetch(`${url}/board/${bno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      console.log(data);

      // 댓글 개수 보여주기
      const replyCnt = document.querySelector(".cnt");
      // document.querySelector(".row .card:nth-child(2) .card-title span")
      // replyList.previousElementSibling.firstElementChild
      replyCnt.innerHTML = data.length + "개의 댓글";

      let result = "";
      data.forEach((reply) => {
        // rno 담아두기. rno을 보내주어야만 삭제, 수정 등이 된다. 그리고 그걸 위해서 data-rno을 넣어주는 것

        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${
          reply.rno
        }" data-email="${reply.replyerEmail}">
          <div class="p-3">
            <img src="/img/user.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" />
          </div>
          <div class="flex-grow-1 align-self-center">
            <div>${reply.replyerName}</div>
            <div>
              <span class="fs-5">${reply.text}</span>
            </div>
            <div class="text-muted">
              <span class="small">${formatDate(reply.createDateTime2)}</span>
            </div>
          </div>
          <div class="flex-grow-1 align-self-center">
            <div class="mb-2">
              <button class="btn btn-outline-danger btn-sm">삭제</button>
            </div>
            <div class="mb-2">
              <button class="btn btn-outline-success btn-sm"">수정</button>
            </div>
          </div>
        </div>`;
      });

      replyList.innerHTML = result;
    })
    .catch((err) => console.log(err));
};

loadReply();

//댓글 삭제 버튼
// document.querySelectorAll(".btn-outline-danger").forEach((btn) => {
//   btn.addEventListener("click", (e) => {
//     const tagetBtn = e.target;

//     tagetBtn.closest(".reply-row").dataset.rno;
//   });
// });

//id="${reply.rno}" 을 버튼에 달아도 제대로 출력되긴한다. 이것으로 이벤트 버블링 하는 것도 가능할 것.

// 이벤트 버블링
replyList.addEventListener("click", (e) => {
  console.log(e.target); // 어느 버튼의 이벤트?
  const btn = e.target;
  // 부모쪽으로 검색해서, data- 검색을 위한 dataset을 쓰고 그것의 rno을 불러라.
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log(rno);
  // 삭제 or 수정
  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;
    // true 인 겨우 삭제요청(fetch)
    fetch(`${url}/${rno}`, {
      method: "DELETE",
      headers: {
        "X-CSRF-TOKEN": csrfVal,
      },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        return res.text();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          Swal.fire({
            title: "데이터 삭제 완료",
            icon: "success",
            draggable: true,
          });
        }
        // 댓글을 삭제하거나 수정한 이후, 전체 페이지를 리프레쉬 할 필요 없이 댓글 호출만 재기동하면 된다.
        // 그를 위하여 아래의 댓글 목록 가져오기를 함수처리 해주어야 한다.
        loadReply();
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    // rno 를 이용해서 reply 가져오기
    const form = document.querySelector("#replyForm");

    fetch(`${url}/${rno}`)
      .then((res) => {
        if (!res.ok) {
          throw new Error(`error! ${res.status}`);
        }
        return res.json();
      })
      .then((data) => {
        // 가져온 reply 를  replyForm 에 보여주기
        console.log(data);
        form.rno.value = data.rno;
        form.text.value = data.text;
        // form.replyer.value = data.replyer;
        // 댓글 작성 버튼 -> 댓글 수정 버튼
        form.rbtn.innerHTML = "댓글수정";
      })
      .catch((err) => console.log(err));
  }
});
