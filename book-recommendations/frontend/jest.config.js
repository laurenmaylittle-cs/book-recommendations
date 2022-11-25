module.exports = {
  preset: '@vue/cli-plugin-unit-jest',
  transformIgnorePatterns: ["node_modules/(?!axios)"],
  setupFiles: ["<rootDir>/tests/unit/index.js"],
  reporters: [
    'default',
    ['./node_modules/jest-html-reporter', {
      pageTitle: 'Test Report',
      outputPath: 'test-report/index.html'
    }]
  ]
}
