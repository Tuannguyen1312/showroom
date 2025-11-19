import React, { useState } from 'react';
import ReCAPTCHA from 'react-google-recaptcha';

const Login = ({ setShowLogin, setShowRegister }) => {
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [captchaVerified, setCaptchaVerified] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setError('');
    setSuccess('');
  };

  const handleCaptcha = (value) => {
    if (value) setCaptchaVerified(true);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (!formData.email.trim() || !formData.password.trim()) {
      setError('Please fill in all fields.');
      return;
    }

    if (!captchaVerified) {
      setError('Please verify the reCAPTCHA.');
      return;
    }

    setSuccess('✅ Login successful (frontend only)');
  };

  return (
    <div
      onClick={() => setShowLogin(false)}
      className="fixed inset-0 bg-black/50 z-50 flex items-center justify-center text-sm"
    >
      <div
        onClick={(e) => e.stopPropagation()}
        className="bg-white rounded-2xl shadow-xl p-8 w-96 relative animate-fadeIn"
      >
        {/* ❌ Nút close */}
        <button
          onClick={() => setShowLogin(false)}
          className="absolute top-3 right-3 text-gray-400 hover:text-gray-600 text-xl"
        >
          ✖
        </button>

        <h2 className="text-3xl font-bold text-center text-gray-800 mb-6">
          Login
        </h2>

        {error && <p className="text-red-500 text-center mb-3">{error}</p>}
        {success && <p className="text-green-600 text-center mb-3">{success}</p>}

        <form onSubmit={handleSubmit}>
          <input
            type="email"
            name="email"
            placeholder="Email Address"
            value={formData.email}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-3 outline-none"
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            className="w-full border border-gray-300 rounded-lg px-3 py-2 mb-4 outline-none"
          />

          {/* 🧠 Google reCAPTCHA */}
          <div className="flex justify-center mb-4">
            <ReCAPTCHA
              sitekey="6LdFzg4sAAAAAC3Xd0LjcaOvxqQXOH6JhJRne5EG"
              onChange={handleCaptcha}
            />
          </div>

          <button
            type="submit"
            className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-2 rounded-lg transition-all"
          >
            Login
          </button>
        </form>

        <p className="text-center text-gray-500 mt-4">
          Don’t have an account?{' '}
          <span
            className="text-indigo-600 hover:underline cursor-pointer"
            onClick={() => {
              setShowLogin(false);
              setShowRegister(true);
            }}
          >
            Sign up
          </span>
        </p>
      </div>
    </div>
  );
};

export default Login;
