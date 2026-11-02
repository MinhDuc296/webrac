async function handleSubmit() {
  const form = document.querySelector('#contact form') || document.querySelector('form');
  const status = document.getElementById('formStatus');

  // Lấy giá trị từ form
  const [fullName, phone, email, topicsSelect, message] = [
    form.querySelector('input[type="text"]'),
    form.querySelector('input[type="tel"]'),
    form.querySelector('input[type="email"]'),
    form.querySelector('select'),
    form.querySelector('textarea')
  ];

  const topics = Array.from(topicsSelect.selectedOptions).map(o => o.textContent.trim());

  const payload = {
    fullName: fullName.value.trim(),
    phone: phone.value.trim(),
    email: email.value.trim(),
    topics: topics,
    message: message.value.trim()
  };

  try {
    const res = await fetch('/api/contacts', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    const data = await res.json();
    if (res.ok && data.success) {
      status.classList.remove('d-none', 'text-danger');
      status.classList.add('text-success');
      status.textContent = 'Cảm ơn bạn! Chúng tôi sẽ liên hệ sớm.';
      form.reset();
    } else {
      throw new Error('Gửi thất bại');
    }
  } catch (e) {
    status.classList.remove('d-none', 'text-success');
    status.classList.add('text-danger');
    status.textContent = 'Xin lỗi, gửi thất bại. Vui lòng thử lại!';
  }
}
