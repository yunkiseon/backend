// 공통 js 삽입

// create.html 의 input 에 multiple accept 추가헌 뒤
const fileInput = document.querySelector("[name='file']");

const showUploadImages = (files) => {
  const output = document.querySelector(".uploadResult ul");

  let tags = "";

  files.forEach((file) => {
    tags += `<li data-name="${file.imgName}" data-path="${file.path}" data-uuid="${file.uuid}">`;
    tags += `<a href="${file.imageURL}">`;
    tags += `<img src="/upload/display?fileName=${file.thumbnailURL}" class="block">`;
    tags += "</a>";
    tags += `<span class="text-sm d-inline-block mx-1">${file.imgName}</span>`;
    tags += `<a href="${file.imageURL}" data-file=""><i class="fa-solid fa-xmark"></i></a>`;
    tags += "</li>";
  });
  output.insertAdjacentHTML("beforeend", tags);
};

fileInput.addEventListener("change", (e) => {
  const files = fileInput.files;
  const formData = new FormData();
  for (let idx = 0; idx < files.length; idx++) {
    formData.append("uploadFiles", files[idx]);
  }
  fetch("/upload/upload", {
    method: "post",
    body: formData,
  })
    .then((res) => res.json())
    .then((data) => {
      console.log(data);
      showUploadImages(data);
    });
});

// 등록 클릭시 form submit
document.querySelector("#createForm").addEventListener("submit", (e) => {
  // submit 정지
  e.preventDefault();
  // uploadResult 안 li  정보 수집 후 form hidden 태그로 append
  const attachInfos = document.querySelectorAll(".uploadResult li");
  let result = "";
  attachInfos.forEach((obj, idx) => {
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}">`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}">`;
    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}">`;
  });
  e.target.insertAdjacentHTML("beforeend", result);
  console.log(e.target.innerHTML);
  e.target.submit();
});
