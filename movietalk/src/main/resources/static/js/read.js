const baseUrl = "/reviews";
const reviewArea = document.querySelector(".reviewList");
const reviewCnt = document.querySelector(".review-cnt");
const reviewForm = document.querySelector("#reviewForm");

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

// 전체 리뷰 가져오기
const reviewList = () => {
  fetch(`${baseUrl}/${mno}/all`)
    .then((res) => {
      if (!res.ok) {
        throw new Error("에러 발생");
      }
      return res.json();
    })
    .then((data) => {
      // 화면 작업
      console.log(data);

      let result = "";
      data.forEach((review) => {
        result += `<div class="d-flex justify-content-between py-2 border-bottom review-row" data-rno="${review.rno}" data-email="${review.email}">`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<div><span class="font-semibold">${review.text}</span></div>`;
        result += `<div class="small text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
        result += `평점 : <span class="grade">${review.grade}</span><div class="starrr"></div></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(
          review.createDate
        )}</span></div></div>`;
        // 로그인 user == 작성자
        if (loginUser === `${review.email}`) {
          result += `<div class="d-flex flex-column align-self-center">`;
          result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
          result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
          result += `</div>`;
        }
        result += `</div>`;
      });
      reviewArea.innerHTML = result;
      reviewCnt.innerHTML = data.length;
    })
    .catch((e) => console.error(e));
};

reviewList();

// 특정 리뷰 삭제
const reviewDelete = (rno, email) => {
  const form = new FormData();
  form.append("email", email);
  console.log("이메이일", email);

  fetch(`${baseUrl}/${mno}/${rno}`, {
    method: "DELETE",
    headers: {
      "X-CSRF-TOKEN": csrfVal,
    },
    body: form,
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error("에러 발생");
      }
      return res.text();
    })
    .then((data) => {
      // 화면 작업
      console.log("delete");
      console.log(data);
      // 화면 갱신
      reviewList();
    })
    .catch((e) => console.error(e));
};

// 특정 리뷰 가져오기
const reviewGet = (rno) => {
  fetch(`${baseUrl}/${mno}/${rno}`)
    .then((res) => {
      if (!res.ok) {
        throw new Error("에러 발생");
      }
      return res.json();
    })
    .then((data) => {
      // 화면 작업
      console.log("get");
      console.log(data);
      reviewForm.nickname.value = data.nickname;
      reviewForm.text.value = data.text;
      reviewForm.rno.value = data.rno;
      reviewForm.mid.value = data.mid;
      reviewForm.mno.value = data.mno;
      reviewForm.email.value = data.email;
      // starrr 은 jquery에서 쓸 수 있는 요소이다. jquery는 라이브러리.
      // 화면 요소를 접근할때 document~add~ 로 길게 코드를 작성해야하는데 jquery는 그렇지 않다
      // ${".section"} 등처럼 간략한 형태가 장점이었다. 또한 ajax도 접근하기 용이했다.
      // 하지만 fetch가 나오고 나선 사용빈도가 줄었다.
      reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
    })
    .catch((e) => console.error(e));
};

//수정 클릭시 -> 삭제도 해서 이벤트 버블링 이용
reviewArea.addEventListener("click", (e) => {
  console.log(e.target); // 어느 버튼의 이벤트?
  const btn = e.target;
  const rno = btn.closest(".review-row").dataset.rno;
  const email = btn.closest(".review-row").dataset.email;
  // 삭제 or 수정
  if (btn.classList.contains("btn-outline-danger")) {
    reviewDelete(rno, email);
  } else if (btn.classList.contains("btn-outline-success")) {
    // 수정
    reviewGet(rno);
  }
});

// --리뷰 수정 + put grade는 form 안의 것이 아니라 read.html 최하단 선언한 grade
// 함수로 만들어서 필요할 때 호출하는 방식
const reviewPut = (form, rno) => {
  const review = {
    rno: rno,
    grade: grade,
    text: form.text.value,
    email: form.email.value,
  };
  fetch(`${baseUrl}/${mno}/${rno}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "X-CSRF-TOKEN": csrfVal,
    },
    body: JSON.stringify(review),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.text();
    })
    .then((data) => {
      // 화면 작업
      console.log("modify");
      console.log(data);
      form.text.value = "";
      form.rno.value = "";
      form.mid.value = "";
      reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();
      reviewList();
    })
    .catch((e) => console.error(e));
};

const reviewPost = (form) => {
  const review = {
    mid: form.mid.value,
    mno: mno,
    grade: grade,
    text: form.text.value,
  };
  fetch(`${baseUrl}/${mno}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "X-CSRF-TOKEN": csrfVal,
    },
    body: JSON.stringify(review),
  })
    .then((res) => {
      if (!res.ok) {
        throw new Error(`에러 발생 ${res.status}`);
      }
      return res.text();
    })
    .then((data) => {
      // 화면 작업
      console.log("new");
      console.log(data);
      form.text.value = "";
      form.querySelector(".starrr a:nth-child(" + grade + ")").click();
      reviewList();
    })
    .catch((e) => console.error(e));
};

// 리뷰 폼 등록 클릭 시 새로운 리뷰 등록 or 기존 리뷰 수정
if (reviewForm) {
  reviewForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const form = e.target;
    const rno = form.rno.value;
    // rno 값 존재하면 수정, 없으면 등록
    if (rno) {
      // 수정
      reviewPut(form, rno);
    } else {
      // 등록
      reviewPost(form);
    }
  });
}

// 큰 이미지 보기
const imgModal = document.getElementById("imgModal");
if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (e) => {
    // 모달을 뜨게 한 li 요소 찾기
    const posterLi = e.relatedTarget;
    // li의 data-* 요소 값 가져오기
    const filePath = posterLi.getAttribute("data-file");
    // If necessary, you could initiate an Ajax request here
    // and then do the updating in a callback.

    // Update the modal's content.
    const modalTitle = imgModal.querySelector(".modal-title");
    const modalBody = imgModal.querySelector(".modal-body");

    modalTitle.textContent = `${title}`;
    modalBody.innerHTML = `<img src="/upload/display?fileName=${filePath}" style="width:100%"></img>`;
  });
}
