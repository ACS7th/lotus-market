/** @type {import('next').NextConfig} */
const nextConfig = {
    transpilePackages: [
      '@cloudscape-design/components',
      '@cloudscape-design/component-toolkit'
    ],
    images: {
      domains: ['storage.googleapis.com', 'kimjichang.site'], 
    },
  };

export default nextConfig;